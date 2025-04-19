package com.example.weissschwarztoolbox.timer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TimerViewModel(application: Application) : AndroidViewModel(application) {

	private val _state = MutableStateFlow(TimerState())
	val state: StateFlow<TimerState> = _state.asStateFlow()

	private var timerJob: Job? = null

	fun startTimer(minutes: Int) {
		val total = minutes * 60 * 1000L
		_state.value = TimerState(
			totalTimeMillis = total,
			remainingTimeMillis = total,
			isRunning = true,
			lastSystemTimestamp = System.currentTimeMillis()
		)
		runTimer()
	}

	fun pauseTimer() {
		timerJob?.cancel()
		_state.update {
			it.copy(
				isRunning = false,
				remainingTimeMillis = it.remainingTimeMillis - (System.currentTimeMillis() - it.lastSystemTimestamp)
			)
		}
	}

	fun resumeTimer() {
		_state.update {
			it.copy(
				isRunning = true,
				lastSystemTimestamp = System.currentTimeMillis()
			)
		}
		runTimer()
	}

	fun resetTimer() {
		timerJob?.cancel()
		_state.value = TimerState()
	}

	private fun runTimer() {
		timerJob?.cancel()
		timerJob = viewModelScope.launch {
			while (_state.value.isRunning && _state.value.remainingTimeMillis > 0L) {
				delay(1000)
				val elapsed = System.currentTimeMillis() - _state.value.lastSystemTimestamp
				val newRemaining = _state.value.remainingTimeMillis - elapsed

				if (newRemaining <= 0L) {
					_state.value = _state.value.copy(
						remainingTimeMillis = 0L,
						isRunning = false
					)
					break
				}

				_state.value = _state.value.copy(
					remainingTimeMillis = newRemaining,
					lastSystemTimestamp = System.currentTimeMillis()
				)
			}
		}
	}
}
