package com.vadmax.timetosleep.domain.repositories.pc

import com.vadmax.timetosleep.domain.usercases.remote.data.GetPingStatus
import com.vadmax.timetosleep.domain.usercases.remote.request.SubscribePinStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import timber.log.Timber

@Single(binds = [PCRepository::class])
class PCRepositoryImpl(
    private val subscribePinStatus: SubscribePinStatus,
    private val getPingStatus: GetPingStatus,
) : PCRepository {

    override val connected = MutableStateFlow(false)

    private lateinit var scope: CoroutineScope

    private var pingJob: Job? = null
    private var setNotConnectedJob: Job? = null

    private val pcExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.w("❌ PC request failed with error: ${throwable.message}")
        connected.value = false
        scope.launch {
            delay(4000)
            resubscribe()
        }
    }

    override fun attachScope(coroutineScope: CoroutineScope) {
        scope = coroutineScope
        resubscribe()
        collectPing()
        logConnection()
    }

    private fun collectPing() {
        scope.launch {
            getPingStatus().collect {
                Timber.d("🌐 Receive ping")
                connected.value = true
                waitForPingTimeout()
            }
        }
    }

    private fun subscribePing() {
        pingJob?.cancel()
        pingJob = scope.launch(pcExceptionHandler) {
            subscribePinStatus()
        }
    }

    private fun resubscribe() {
        setNotConnectedJob?.cancel()
        pingJob?.cancel()
        subscribePing()
    }

    private fun waitForPingTimeout() {
        setNotConnectedJob?.cancel()
        setNotConnectedJob = scope.launch {
            delay(5000)
            Timber.d("⚠️ Wait for ping timeout")
            connected.value = false
            resubscribe()
        }
    }

    private fun logConnection() {
        scope.launch {
            connected.collectLatest {
                if (it) {
                    Timber.i("✅ Connected")
                } else {
                    Timber.i("⚠️ Not connected")
                }
            }
        }
    }
}
