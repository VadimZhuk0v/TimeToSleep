package com.vadmax.timetosleep.domain.usercases

import com.vadmax.io.domain.usercases.SetTimerEnable

class ApplyActions(
    private val setTimerEnable: SetTimerEnable,
    private val stopMusic: StopMusic,
    private val closeApps: CloseApps,
) {

    suspend operator fun invoke() {
        setTimerEnable(false)
        stopMusic()
    }
}
