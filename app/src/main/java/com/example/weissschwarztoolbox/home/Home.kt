package com.example.weissschwarztoolbox.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
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
fun HomeScreen(navController: NavController) {
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
					title = "Weiss Schwarz Toolbox",
				)
			},
		) { padding ->
			Box(
				Modifier
					.padding(padding)
					.fillMaxSize(),
				contentAlignment = Alignment.Center
			) {

				Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


					Text(text = "更多功能开发中...")
					Text(text = "In Progress...")

					val context = LocalContext.current
					val imageLoader = ImageLoader.Builder(context)
						.components {
							add(GifDecoder.Factory())
						}
						.build()

					AsyncImage(
						model = ImageRequest.Builder(context)
							.data("file:///android_asset/ume_kotone.gif")
							.build(),
						imageLoader = imageLoader,
						contentDescription = "ume dance",
						modifier = Modifier
							.padding(10.dp)
							.fillMaxWidth(0.5f)
							.align(Alignment.CenterHorizontally)
					)

					val uriHandler = LocalUriHandler.current
					Text(
						text = "Github Link",
						color = Color.Gray,
						fontSize = MaterialTheme.typography.titleMedium.fontSize,
						fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
						modifier = Modifier
							.padding(10.dp)
							.clickable { uriHandler.openUri("https://github.com/llloydretro2/WeissSchwarzToolbox/releases") },
					)

				}
			}
		}
	}
}
