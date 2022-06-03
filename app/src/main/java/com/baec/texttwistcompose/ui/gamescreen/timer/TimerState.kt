package com.baec.texttwistcompose.ui.gamescreen.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class TimerState {
    var currentTime: Long by mutableStateOf(0L)
    var totalTime: Long by mutableStateOf(0L)
    var isActive: Boolean by mutableStateOf(false)
}