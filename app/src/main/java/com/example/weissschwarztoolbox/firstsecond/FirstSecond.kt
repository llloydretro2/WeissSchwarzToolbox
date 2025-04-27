package com.example.weissschwarztoolbox.firstsecond

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.R
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun FirstSecond(navController: NavController) {


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
					title = "First/Second",
				)
			},
		) { padding ->

			FirstSecondContent(padding)

		}
	}
}


@OptIn(ExperimentalUuidApi::class)
@Composable
fun FirstSecondContent(padding: PaddingValues) {

	var cardRevealed by remember { mutableStateOf(false) }
	var firstSecond by remember { mutableStateOf(false) }

	Column(
		modifier = Modifier
			.padding(padding)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {

		if (cardRevealed) {

			var cardImg = 0
			cardImg = if (firstSecond) {
				R.drawable.ims_s
			} else {
				R.drawable.ims_k
			}

			Image(
				painter = painterResource(id = cardImg),
				contentDescription = "first/second",
				contentScale = ContentScale.Companion.Crop,
				modifier = Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(4.dp))
					.padding(10.dp)
			)


		}

		if (!cardRevealed) {
			Button(
				onClick = {
					firstSecond = Random.nextBoolean()
					cardRevealed = true
				},
				content = { Text("Decide First/Second") }
			)
		}


	}


}
