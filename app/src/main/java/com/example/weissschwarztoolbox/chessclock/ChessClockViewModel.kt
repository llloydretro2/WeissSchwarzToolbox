package com.example.weissschwarztoolbox.chessclock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChessClockViewModel : ViewModel() {

	private val _state = MutableStateFlow(ChessClockState())
	val state: StateFlow<ChessClockState> = _state

	private var timerJob: Job? = null

	fun startPlayer(player: Player) {
		if (_state.value.activePlayer == player) return // already running

		updateElapsedTime()

		_state.update {
			it.copy(
				activePlayer = player,
				lastStartTime = System.currentTimeMillis()
			)
		}

		runTimer()
	}

	fun pause() {
		updateElapsedTime()
		_state.update { it.copy(activePlayer = null) }
		timerJob?.cancel()
	}

	fun reset() {
		timerJob?.cancel()
		_state.value = ChessClockState()
	}

	private fun updateElapsedTime() {
		val now = System.currentTimeMillis()
		val elapsed = now - _state.value.lastStartTime

		_state.update {
			when (it.activePlayer) {
				Player.A -> it.copy(timePlayerA = it.timePlayerA + elapsed)
				Player.B -> it.copy(timePlayerB = it.timePlayerB + elapsed)
				else -> it
			}
		}
	}

	private fun runTimer() {
		timerJob?.cancel()
		timerJob = viewModelScope.launch {
			while (true) {
				delay(1000)
				updateElapsedTime()
				_state.update {
					it.copy(lastStartTime = System.currentTimeMillis())
				}
			}
		}
	}
}
