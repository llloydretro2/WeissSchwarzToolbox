package com.example.weissschwarztoolbox.chessclock

data class ChessClockState(
	val timePlayerA: Long = 0L,
	val timePlayerB: Long = 0L,
	val activePlayer: Player? = null,
	val lastStartTime: Long = 0L
)

enum class Player {
	A, B
}
