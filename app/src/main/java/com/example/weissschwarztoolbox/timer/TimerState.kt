package com.example.weissschwarztoolbox.timer

import kotlinx.serialization.Serializable

@Serializable
data class TimerState(
	val totalTimeMillis: Long = 0L,
	val remainingTimeMillis: Long = 0L,
	val isRunning: Boolean = false,
	val lastSystemTimestamp: Long = 0L
)
