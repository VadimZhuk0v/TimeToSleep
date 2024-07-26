package com.vadmax.timetosleep.utils.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

interface EventFlow<T> : Flow<T> {
    suspend fun collectLatest(action: suspend (value: T) -> Unit)

    suspend fun collect(action: suspend (value: T) -> Unit)

    override suspend fun collect(collector: FlowCollector<T>)

    suspend fun collectLatest(collector: FlowCollector<T>)
}

class MutableEventFlow<T>(extraBufferCapacity: Int = 3) :
    MutableSharedFlow<T>,
    EventFlow<T> {

    private val flow = MutableSharedFlow<T>(
        extraBufferCapacity = extraBufferCapacity,
        onBufferOverflow = BufferOverflow.SUSPEND,
        replay = 1,
    )

    override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    override suspend fun emit(value: T) {
        flow.emit(value)
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        flow.resetReplayCache()
    }

    override fun tryEmit(value: T): Boolean = flow.tryEmit(value)

    override val replayCache: List<T>
        get() = flow.replayCache

    @ExperimentalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect {
            resetReplayCache()
            collector.emit(it)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun collectLatest(action: suspend (value: T) -> Unit) {
        flow.collectLatest {
            resetReplayCache()
            action(it)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun collect(action: suspend (value: T) -> Unit) {
        flow.collect {
            resetReplayCache()
            action(it)
        }
    }

    @ExperimentalCoroutinesApi
    override suspend fun collectLatest(collector: FlowCollector<T>) {
        flow.collectLatest {
            resetReplayCache()
            collector.emit(it)
        }
    }
}

@Composable
fun <T : Any> SingleEventEffect(
    sideEffectFlow: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow) {
        lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
            sideEffectFlow.collect(collector)
        }
    }
}
