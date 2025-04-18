package com.example.weissschwarztoolbox.choosepack

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch

@Composable
fun ChoosePack(navController: NavController) {

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
					title = "Pick a Pack",
				)
			},
		) { padding ->

			ChoosePackContent(padding)
		}
	}
}

@Composable
fun ChoosePackContent(padding: PaddingValues) {

	var totalBox by remember { mutableIntStateOf(0) }
	var numbBox by remember { mutableIntStateOf(0) }
	var packList by remember { mutableStateOf(listOf<String>()) }

	Column(modifier = Modifier.padding(padding)) {

		Row(modifier = Modifier.padding(10.dp)) {

			OutlinedTextField(
				value = totalBox.toString(),
				onValueChange = {
					try {
						totalBox = it.toInt()
					} catch (e: NumberFormatException) {
						Log.d("ChoosePack", "Error: $e")
					}
				},
				label = { Text("Total Box") },
				modifier = Modifier
					.weight(1f)
					.padding(10.dp)
			)

			OutlinedTextField(
				value = numbBox.toString(),
				onValueChange = {
					try {
						numbBox = it.toInt()
					} catch (e: NumberFormatException) {
						Log.d("ChoosePack", "Error: $e")
					}
				},
				label = { Text("How many you want?") },
				modifier = Modifier
					.weight(1f)
					.padding(10.dp)
			)
		}

		Button(
			onClick = {
				if (totalBox > 0 && numbBox in 1..totalBox) {
					val allPacks = (1..totalBox).toList()
					val randomPicked = allPacks.shuffled().take(numbBox).sorted()
					packList = randomPicked.map { "$it" }
				}
			},
			modifier = Modifier
				.padding(10.dp)
				.fillMaxWidth()
		) {
			Text(text = "Pick Pack")
		}

		if (packList.isNotEmpty()) {

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(10.dp)
					.background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))
			) {

				Column(modifier = Modifier
					.fillMaxWidth()
					.padding(10.dp)) {
					Text(
						text = "Pick",
						color = MaterialTheme.colorScheme.onPrimary,
						fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
						fontSize = MaterialTheme.typography.headlineLarge.fontSize
					)

					Column(
						modifier = Modifier
							.padding(10.dp)
							.fillMaxWidth(),
						horizontalAlignment = Alignment.CenterHorizontally,
						verticalArrangement = Arrangement.Center
					) {

						packList.forEach { pack ->
							Text(
								text = pack,
								color = MaterialTheme.colorScheme.onPrimary,
								fontWeight = MaterialTheme.typography.headlineSmall.fontWeight,
								fontSize = MaterialTheme.typography.headlineSmall.fontSize
							)
						}
					}

					val context = LocalContext.current
					val imageLoader = ImageLoader.Builder(context)
						.components {
							add(GifDecoder.Factory())
						}
						.build()


					Box(modifier = Modifier
						.fillMaxWidth()
						.padding(10.dp), contentAlignment = Alignment.Center) {
						Text(
							text = "Good Luck",
							color = MaterialTheme.colorScheme.onPrimary,
							fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
							fontSize = MaterialTheme.typography.headlineLarge.fontSize,
						)
					}



					AsyncImage(
						model = ImageRequest.Builder(context)
							.data("file:///android_asset/ume_dance.gif")
							.build(),
						imageLoader = imageLoader,
						contentDescription = "ume dance",
						modifier = Modifier
							.padding(10.dp)
							.fillMaxWidth(0.5f)
							.align(Alignment.CenterHorizontally)
					)


				}

			}
		}

	}
}
