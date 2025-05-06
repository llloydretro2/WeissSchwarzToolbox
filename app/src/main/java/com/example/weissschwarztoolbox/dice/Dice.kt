package com.example.weissschwarztoolbox.dice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun Dice(navController: NavController) {

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
					title = "Dice",
				)
			},
		) { padding ->

			DiceContent(padding = padding)

		}
	}

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiceContent(padding: PaddingValues) {

	var numbDiceInput by remember { mutableStateOf("1") }
	var numSidesInput by remember { mutableStateOf("6") }
	var diceList by remember { mutableStateOf(listOf<Int>()) }


	Column(modifier = Modifier
		.padding(padding)
		.fillMaxSize()) {

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {

			OutlinedTextField(
				value = numbDiceInput,
				onValueChange = {
					if (it.all { c -> c.isDigit() } || it.isEmpty()) {
						numbDiceInput = it
					}
				},
				label = { Text("# Dice") },
				modifier = Modifier
					.weight(1f)
					.padding(10.dp)
			)



			Box(
				modifier = Modifier
					.size(40.dp)
					.background(color = Color.LightGray, shape = CircleShape),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = "d",
					textAlign = TextAlign.Center
				)
			}


			OutlinedTextField(
				value = numSidesInput,
				onValueChange = {
					if (it.all { c -> c.isDigit() } || it.isEmpty()) {
						numSidesInput = it
					}
				},
				label = { Text("# Sides") },
				modifier = Modifier
					.weight(1f)
					.padding(10.dp)
			)
		}


		Button(
			onClick = { diceList = List(numbDiceInput.toInt()) { (1..numSidesInput.toInt()).random() } },
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp)

		) {
			Text("Roll")
		}


		if (diceList.sum() > 0) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = diceList.sum().toString(),
					style = MaterialTheme.typography.displayLarge
				)
			}
		}


		FlowRow(
			modifier = Modifier.fillMaxWidth(),
			maxItemsInEachRow = 4,
			horizontalArrangement = Arrangement.Center,
		) {
			diceList.forEach { value ->
				DiceComponent(value = value)
			}
		}


	}


}

@Composable
fun DiceComponent(value: Int) {
	Box(
		modifier = Modifier
			.size(80.dp)
			.padding(4.dp)
			.border(
				width = 2.dp,
				color = Color.Black,
				shape = RoundedCornerShape(16.dp)
			),
		contentAlignment = Alignment.Center
	) {

		Text(
			text = value.toString(),
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.displaySmall
		)
	}
}


