package com.example.weissschwarztoolbox.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun Timer(navController: NavController) {

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
					title = "Timer",
				)
			},
		) { padding ->
			TimerContent(
				padding = padding
			)
		}


	}
}

@Composable
fun TimerContent(
	padding: PaddingValues,
	viewModel: TimerViewModel = viewModel()
) {


	val timerState by viewModel.state.collectAsState()
	var inputMinutes by rememberSaveable { mutableStateOf("35") }


	val minutes = (timerState.remainingTimeMillis / 1000) / 60
	val seconds = (timerState.remainingTimeMillis / 1000) % 60

	Column(
		horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
			.fillMaxSize()
			.padding(padding)
	) {

		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {


			Button(
				onClick = { },
				contentPadding = PaddingValues(0.dp),
				modifier = Modifier.size(36.dp)
			) {
				Text("-")
			}


			OutlinedTextField(
				modifier = Modifier
					.padding(10.dp)
					.width(100.dp),
				value = inputMinutes,
				onValueChange = { inputMinutes = it.filter { it.isDigit() } },
				label = { Text("Minutes") }
			)


			Button(
				onClick = { },
				contentPadding = PaddingValues(0.dp),
				modifier = Modifier.size(36.dp)
			) {
				Text("+")
			}


		}


		Text(
			text = "%02d:%02d".format(minutes, seconds),
			style = MaterialTheme.typography.headlineLarge,
			modifier = Modifier.padding(vertical = 32.dp)
		)

		Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
			if (!timerState.isRunning && timerState.remainingTimeMillis == 0L) {
				Button(onClick = {
					viewModel.startTimer(inputMinutes.toIntOrNull() ?: 0)
				}) {
					Text("Start")
				}
			} else if (timerState.isRunning) {
				Button(onClick = { viewModel.pauseTimer() }) {
					Text("Pause")
				}
			} else {
				Button(onClick = { viewModel.resumeTimer() }) {
					Text("Resume")
				}
			}

			Button(onClick = { viewModel.resetTimer() }) {
				Text("Reset")
			}
		}
	}



}
