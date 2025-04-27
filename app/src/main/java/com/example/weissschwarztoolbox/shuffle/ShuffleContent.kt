package com.example.weissschwarztoolbox.shuffle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weissschwarztoolbox.R

@Composable
fun ShuffleContent(padding: PaddingValues) {

	var showButton by remember { mutableStateOf(true) }
	var pileNumber by remember { mutableIntStateOf(0) }
	var pileList = remember { mutableStateListOf<Int>() }

	var pileLow = 7
	var pileHigh = 10

	Column(
		modifier = Modifier.Companion
			.padding(padding)
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.Companion.CenterHorizontally
	) {
		if (showButton) {
			Button(
				onClick = {
					pileNumber = (pileLow..pileHigh).random()

					pileList.clear()
					repeat(pileNumber) {
						pileList.add(0)
					}

					repeat(50) {
						val randomIndex = (0 until pileNumber).random()
						pileList[randomIndex] = pileList[randomIndex] + 1
					}

					showButton = false
				},
				content = {
					Text("Generate Shuffle")
				}
			)
		} else {

			LazyVerticalGrid(
				columns = GridCells.Fixed(5),
				modifier = Modifier.Companion
					.padding(16.dp)
			) {
				items(pileList.size) { index ->
					Box(
						modifier = Modifier.Companion
							.size(100.dp)
							.padding(4.dp)
					) {
						Image(
							painter = painterResource(id = R.drawable.weiss_card_back),
							contentDescription = "Card Background",
							contentScale = ContentScale.Companion.Crop,
							modifier = Modifier.Companion
								.fillMaxSize()
								.clip(RoundedCornerShape(4.dp))
								.alpha(0.5f)
						)
						Column(
							modifier = Modifier.Companion
								.align(Alignment.Companion.Center),
							horizontalAlignment = Alignment.Companion.CenterHorizontally
						) {
							Text(text = "Pile ${index + 1}", color = Color.Companion.Black)
							Text(text = "${pileList[index]} cards", color = Color.Companion.Black)
						}
					}

				}
			}

		}
	}
}
