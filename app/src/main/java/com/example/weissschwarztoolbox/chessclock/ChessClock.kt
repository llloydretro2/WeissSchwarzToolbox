package com.example.weissschwarztoolbox.chessclock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun ChessClock(navController: NavController, chessClockViewModel: ChessClockViewModel) {

	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val coroutineScope = rememberCoroutineScope()


	ModalNavigationDrawer(
		drawerState = drawerState,
		drawerContent = {
			DrawerContent(
				navController = navController,
				onItemClick = {
					coroutineScope.launch { drawerState.close() }
				},
			)
		},
	) {
		Scaffold(
			topBar = {
				TopBarComponent(
					menuClick = {
						coroutineScope.launch {
							drawerState.open()
						}
					},
					title = "Chess Clock",
				)
			},
		) { padding ->
			ChessClockContent(padding = padding, chessClockViewModel = chessClockViewModel)
		}


	}
}

@Composable
fun ChessClockContent(padding: PaddingValues, chessClockViewModel: ChessClockViewModel) {

	val state by chessClockViewModel.state.collectAsState()
	var resetDialog by remember { mutableStateOf(false) }

	val activeColorA = Color(0xFFB71C1C)
	val inactiveColorA = Color(0xFFFFCDD2)

	val activeColorB = Color(0xFF0D47A1)
	val inactiveColorB = Color(0xFFBBDEFB)

	fun formatTime(ms: Long): String {
		val sec = ms / 1000
		return "%02d:%02d".format(sec / 60, sec % 60)
	}

	Column(
		modifier = Modifier
			.padding(padding)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {

		Box(
			modifier = Modifier
				.padding(20.dp)
				.fillMaxWidth()
				.background(
					color = if (state.activePlayer == Player.A) activeColorA else inactiveColorA,
					shape = RoundedCornerShape(10.dp)
				)
				.clickable {
					chessClockViewModel.startPlayer(Player.A)
				}
				.height(250.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "${formatTime(state.timePlayerA)}",
				color = Color.White
			)
		}

		Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
			Button(onClick = { chessClockViewModel.pause() }) { Text("Pause") }
			Button(onClick = { resetDialog = true }) { Text("Reset") }
		}

		Box(
			modifier = Modifier
				.padding(20.dp)
				.fillMaxWidth()
				.background(
					color = if (state.activePlayer == Player.B) activeColorB else inactiveColorB,
					shape = RoundedCornerShape(10.dp)
				)
				.clickable {
					chessClockViewModel.startPlayer(Player.B)
				}
				.height(250.dp),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "${formatTime(state.timePlayerB)}",
				color = Color.White
			)
		}
	}

	if (resetDialog) {
		AlertDialog(
			onDismissRequest = { resetDialog = false },
			title = { Text("Reset Clock") },
			text = { Text("Are you sure you want to reset both timers?") },
			confirmButton = {
				Button(
					onClick = {
						chessClockViewModel.reset()
						resetDialog = false
					},
					colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
				) {
					Text("Yes", color = Color.White)
				}
			},
			dismissButton = {
				Button(onClick = { resetDialog = false }) {
					Text("Cancel")
				}
			}
		)
	}




}
