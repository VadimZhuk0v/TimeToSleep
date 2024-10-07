package com.vadmax.timetosleep.domain.repositories.pc

import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.usercases.local.HasServerConfig
import com.vadmax.timetosleep.domain.usercases.local.SetServerConfig
import com.vadmax.timetosleep.domain.usercases.remote.data.GetPCTime
import com.vadmax.timetosleep.domain.usercases.remote.data.GetPCTimerEnabled
import com.vadmax.timetosleep.domain.usercases.remote.data.GetPingStatus
import com.vadmax.timetosleep.domain.usercases.remote.request.SendTimeToServer
import com.vadmax.timetosleep.domain.usercases.remote.request.SendTimerEnable
import com.vadmax.timetosleep.domain.usercases.remote.request.SubscribePCTimerEnabled
import com.vadmax.timetosleep.domain.usercases.remote.request.SubscribePinStatus
import com.vadmax.timetosleep.domain.usercases.remote.request.SubscribeToPCTime
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import com.vadmax.timetosleep.utils.toDomainModel
import com.vadmax.timetosleep.utils.toUIModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import timber.log.Timber

@Single(binds = [PCRepository::class])
class PCRepositoryImpl(
    getPCTimerEnabled: GetPCTimerEnabled,
    private val coroutineScope: CoroutineScope,
    private val subscribePinStatus: SubscribePinStatus,
    private val subscribeToPCTime: SubscribeToPCTime,
    private val getPCTime: GetPCTime,
    private val getPingStatus: GetPingStatus,
    private val sendTimeToServer: SendTimeToServer,
    private val subscribePCTimerEnabled: SubscribePCTimerEnabled,
    private val sendTimerEnable: SendTimerEnable,
    private val hasServerConfig: HasServerConfig,
    private val setServerConfig: SetServerConfig,
) : PCRepository {

    override val connected = MutableStateFlow(false)

    override val selectTime = MutableEventFlow<TimeUIModel>()

    override val enabled = getPCTimerEnabled()

    private val selectedTime = MutableStateFlow<TimeUIModel?>(null)

    override val event = MutableSharedFlow<PCTimerRepositoryEvent>()

    override val timerState = combine(
        connected,
        selectedTime,
        hasServerConfig(),
    ) { connected, selectTime, hasServerConfig ->
        if (hasServerConfig.not()) {
            Timber.d("No server config")
            return@combine TimerState.NoDevice
        }
        if (connected.not() || selectTime == null) {
            return@combine TimerState.Idle
        }
        TimerState.Timer(selectTime)
    }.distinctUntilChangedBy {
        it::class
    }.onEach {
        Timber.d("Timer state: $it")
    }

    private var uiCoroutineScope: CoroutineScope? = null
    private var pingJob: Job? = null
    private var collectPingJob: Job? = null
    private var pcTimeJob: Job? = null
    private var collectPCTimeJob: Job? = null
    private var pcTimerEnabledJob: Job? = null
    private var setNotConnectedJob: Job? = null
    private var sendTimeJob: Job? = null
    private var listenServerConfigJob: Job? = null

    private val pcExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.w("❌ PC request failed with error: ${throwable.message}")
        connected.value = false
        uiCoroutineScope?.launch {
            delay(2000)
            resubscribe()
        }
    }

    init {
        logConnection()
    }

    override fun attachScope(coroutineScope: CoroutineScope) {
        uiCoroutineScope = coroutineScope
        listenServerConfig()
    }

    override fun detachScope() {
        cancelAllJobs()
        listenServerConfigJob?.cancel()
        uiCoroutineScope = null
    }

    override suspend fun changeTimeByUser(time: TimeUIModel) {
        selectedTime.value = time
    }

    override fun setEnabled(value: Boolean) {
        uiCoroutineScope?.launch {
            sendTimerEnable(value)
        }
    }

    override fun setServerConfig(data: String) {
        coroutineScope.launch {
            setServerConfig.invoke(data)
                .onFailure {
                    event.emit(PCTimerRepositoryEvent.UnsupportedQR)
                }
        }
    }

    private fun collectPing() {
        collectPingJob?.cancel()
        collectPingJob = uiCoroutineScope?.launch {
            getPingStatus().collect {
                connected.value = true
                waitForPingTimeout()
            }
        }
    }

    private fun collectPCTime() {
        collectPCTimeJob?.cancel()
        collectPCTimeJob = uiCoroutineScope?.launch {
            getPCTime().collectLatest {
                Timber.i("💻 🕰️: $it")
                selectedTime.value = it.toUIModel()
                selectTime.emit(it.toUIModel())
            }
        }
    }

    private fun subscribePing() {
        pingJob?.cancel()
        pingJob = uiCoroutineScope?.launch(pcExceptionHandler) {
            subscribePinStatus()
        }
    }

    private fun subscribePCTime() {
        pcTimeJob?.cancel()
        pcTimeJob = uiCoroutineScope?.launch(pcExceptionHandler) {
            subscribeToPCTime()
        }
    }

    private fun cancelAllJobs() {
        setNotConnectedJob?.cancel()
        pingJob?.cancel()
        pcTimeJob?.cancel()
        sendTimeJob?.cancel()
        pcTimerEnabledJob?.cancel()
        collectPingJob?.cancel()
        collectPCTimeJob?.cancel()
        connected.value = false
        selectedTime.value = null
    }

    private fun resubscribe() {
        cancelAllJobs()
        collectPing()
        collectPCTime()
        subscribePing()
        shareTime()
        subscribePCTime()
        subscribePCTimerEnabled()
    }

    private fun waitForPingTimeout() {
        setNotConnectedJob?.cancel()
        setNotConnectedJob = coroutineScope.launch {
            delay(3000)
            Timber.d("⚠️ Ping timeout")
            connected.value = false
            resubscribe()
        }
    }

    private fun logConnection() {
        coroutineScope.launch {
            connected.collectLatest {
                if (it) {
                    Timber.i("✅ Connected")
                } else {
                    Timber.i("⚠️ Not connected")
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun shareTime() {
        sendTimeJob?.cancel()
        sendTimeJob = uiCoroutineScope?.launch(pcExceptionHandler) {
            sendTimeToServer(
                selectedTime
                    .debounce(500)
                    .filterNotNull()
                    .map { it.toDomainModel() },
            )
        }
    }

    private fun subscribePCTimerEnabled() {
        pcTimerEnabledJob?.cancel()
        pcTimerEnabledJob = uiCoroutineScope?.launch(pcExceptionHandler) {
            subscribePCTimerEnabled.invoke()
        }
    }

    private fun listenServerConfig() {
        listenServerConfigJob = uiCoroutineScope?.launch {
            hasServerConfig().distinctUntilChanged().collectLatest {
                if (it) {
                    resubscribe()
                } else {
                    cancelAllJobs()
                }
            }
        }
    }
}
