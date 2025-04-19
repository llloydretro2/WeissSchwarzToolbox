package com.example.weissschwarztoolbox.packtracker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.reflect.full.memberProperties

@Composable
fun PackTracker(navController: NavController) {
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	val coroutineScope = rememberCoroutineScope()
	val scrollState = rememberScrollState()

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
					title = "Pack Tracker",
				)
			},
		) { padding ->

			Column(
				modifier = Modifier.verticalScroll(scrollState),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				PackStatContent(paddings = padding)
			}
		}
	}
}

@Composable
fun PackStatContent(paddings: PaddingValues) {
	Column(
		modifier =
			Modifier
				.padding(paddings)
				.fillMaxSize(),
	) {
		PackInputFields()

		PackStats()
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PackStats() {
	val packStatViewModel: PackStatViewModel = viewModel()
	val packStat by packStatViewModel.packStat.collectAsState()
	var totalCards by remember { mutableIntStateOf(0) }
	totalCards = packStat.packs * packStat.cardsPerPack
	var showClearDialog by remember { mutableStateOf(false) }

	val rarityList =
		listOf(
			"agr",
			"sir",
			"secPlus",
			"sec",
			"ssp",
			"sp",
			"sr",
			"setRarity",
			"rrrPlus",
			"rrr",
			"rr",
			"r",
		)

	val rarityDisplayMap =
		mapOf(
			"agr" to "AGR",
			"sir" to "SIR",
			"secPlus" to "SEC+",
			"sec" to "SEC",
			"ssp" to "SSP",
			"sp" to "SP",
			"sr" to "SR",
			"setRarity" to "Set Rarity",
			"rrrPlus" to "RRR+",
			"rrr" to "RRR",
			"rr" to "RR",
			"r" to "R",
		)

	val pieData =
		rarityList
			.associateWith { rarity ->
				PackStat::class.memberProperties.first { it.name == rarity }.get(packStat) as? Int ?: 0
			}.filterValues { it > 0 }
			.mapValues { it.value.toFloat() }

	val pieColors =
		listOf(
			Color(0xFFEF476F),
			Color(0xFF06D6A0),
			Color(0xFFFFD166),
			Color(0xFF118AB2),
			Color(0xFF073B4C),
			Color(0xFFA3A380),
			Color(0xFFD9BF77),
			Color(0xFFC44536),
			Color(0xFF6A0572),
			Color(0xFF8AC926),
			Color(0xFFFF7F11),
			Color(0xFF1982C4),
		)

	Box(
		modifier =
			Modifier
				.padding(10.dp)
				.fillMaxWidth()
				.background(
					color = MaterialTheme.colorScheme.primary,
					shape = RoundedCornerShape(30.dp),
				),
		contentAlignment = Alignment.Center,
	) {
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				text = "Total Cards: $totalCards",
				color = MaterialTheme.colorScheme.onPrimary,
				modifier = Modifier.padding(10.dp),
				fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
				fontSize = MaterialTheme.typography.titleLarge.fontSize,
			)

			RarityStatsTableComposable(
				rarityList = rarityList,
				displayMap = rarityDisplayMap,
				packStat = packStat,
				totalCards = totalCards,
			)

			if (pieData.isNotEmpty()) {
				pieChartWithLegend(
					data = pieData,
					displayMap = rarityDisplayMap,
					colors = pieColors,
					totalCards = totalCards
				)
			}

			Button(
				modifier = Modifier.padding(bottom = 20.dp),
				onClick = { showClearDialog = true },
				colors = ButtonDefaults.buttonColors(Color.Red),
			) {
				Text("Clear")
			}

			if (showClearDialog) {
				AlertDialog(
					onDismissRequest = { showClearDialog = false },
					title = { Text("Confirm Clear") },
					text = { Text("Are you sure you want to clear all recorded data?") },
					confirmButton = {
						Button(
							colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
							onClick = {
								packStatViewModel.reset()
								showClearDialog = false
							},
						) {
							Text("Yes")
						}
					},
					dismissButton = {
						Button(onClick = { showClearDialog = false }) {
							Text("Cancel")
						}
					},
				)
			}
		}
	}
}

@Composable
fun pieChartWithLegend(
	data: Map<String, Float>,
	displayMap: Map<String, String>,
	colors: List<Color>,
	modifier: Modifier = Modifier,
	chartSize: Dp = 200.dp,
	totalCards: Int
) {
	val rarityTotal = data.values.sum()
	val others = totalCards - rarityTotal

	val fullData = if (others > 0f) {
		data + ("others" to others)
	} else {
		data
	}

	var startAngle = -90f

	Row(
		modifier = modifier.padding(16.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Canvas(
			modifier = Modifier.size(chartSize),
		) {
			fullData.entries.forEachIndexed { index, entry ->
				val sweepAngle = (entry.value / totalCards) * 360f
				drawArc(
					color = colors.getOrElse(index) { Color.Gray },
					startAngle = startAngle,
					sweepAngle = sweepAngle,
					useCenter = true,
				)
				startAngle += sweepAngle
			}
		}

		Spacer(modifier = Modifier.width(24.dp))

		Column(
			verticalArrangement = Arrangement.spacedBy(8.dp),
		) {
			data.entries.forEachIndexed { index, entry ->
				val label = displayMap[entry.key] ?: entry.key
				val color = colors.getOrElse(index) { Color.Gray }

				Row(verticalAlignment = Alignment.CenterVertically) {
					Box(
						modifier =
							Modifier
								.size(12.dp)
								.background(color = color, shape = RoundedCornerShape(2.dp)),
					)
					Spacer(modifier = Modifier.width(8.dp))
					Text(
						text = label,
						color = MaterialTheme.colorScheme.onPrimary,
						style = MaterialTheme.typography.bodySmall,
					)
				}
			}
		}
	}
}

@Composable
fun RarityStatsTableComposable(
	rarityList: List<String>,
	displayMap: Map<String, String>,
	packStat: PackStat,
	totalCards: Int,
) {
	val headerTextStyle =
		MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)

	Column(
		modifier =
			Modifier
				.padding(10.dp)
				.fillMaxWidth()
				.background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		// 表头
		Row(modifier = Modifier.fillMaxWidth()) {
			Text(
				"Rarity",
				modifier =
					Modifier
						.weight(1f)
						.padding(8.dp)
						.wrapContentWidth(Alignment.CenterHorizontally),
				style = headerTextStyle,
			)
			Text(
				"Count",
				modifier =
					Modifier
						.weight(1f)
						.padding(8.dp)
						.wrapContentWidth(Alignment.CenterHorizontally),
				style = headerTextStyle,
			)
			Text(
				"Rate (%)",
				modifier =
					Modifier
						.weight(1f)
						.padding(8.dp)
						.wrapContentWidth(Alignment.CenterHorizontally),
				style = headerTextStyle,
			)
		}

		// 表体
		rarityList.forEach { rarity ->
			val value =
				PackStat::class.memberProperties.first { it.name == rarity }.get(packStat) as? Int
					?: 0

			if (value > 0) {
				val rate = if (totalCards > 0) value.toFloat() / totalCards * 100f else 0f
				val displayRate = String.format(Locale.US, "%.2f", rate)

				Row(
					modifier = Modifier.fillMaxWidth(),
				) {
					Text(
						displayMap[rarity] ?: rarity,
						modifier =
							Modifier
								.weight(1f)
								.padding(8.dp)
								.wrapContentWidth(Alignment.CenterHorizontally),
						color = MaterialTheme.colorScheme.onPrimary,
					)
					Text(
						value.toString(),
						modifier =
							Modifier
								.weight(1f)
								.padding(8.dp)
								.wrapContentWidth(Alignment.CenterHorizontally),
						color = MaterialTheme.colorScheme.onPrimary,
					)
					Text(
						"$displayRate %",
						modifier =
							Modifier
								.weight(1f)
								.padding(8.dp)
								.wrapContentWidth(Alignment.CenterHorizontally),
						color = MaterialTheme.colorScheme.onPrimary,
					)
				}
			}
		}
	}
}

@Composable
fun PackInputFields() {
	val packStatViewModel: PackStatViewModel = viewModel()

	Box(
		modifier = Modifier.fillMaxWidth(),
	) {
		// input field
		Column(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			// pack input
			Box(
				modifier =
					Modifier
						.padding(10.dp)
						.fillMaxWidth(),
			) {
				PackNumInputField(packStatViewModel = packStatViewModel)
			}

			// rarity input
			Box(
				modifier = Modifier.fillMaxWidth(),
			) {
				PackRarityInputField()
			}
		}
	}
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PackRarityInputField() {
	val packStatViewModel: PackStatViewModel = viewModel()
	val packStat by packStatViewModel.packStat.collectAsState()

	Column() {

		FlowRow(
			modifier = Modifier.fillMaxWidth(),
			maxItemsInEachRow = 3,
			horizontalArrangement = Arrangement.Center,
			verticalArrangement = Arrangement.spacedBy(1.dp),
		) {
			RarityCounterTile(label = "AGR", count = packStat.agr, countChange = {
				try {
					packStatViewModel.updateAgr(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SIR", count = packStat.sir, countChange = {
				try {
					packStatViewModel.updateSir(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SEC+", count = packStat.secPlus, countChange = {
				try {
					packStatViewModel.updateSecPlus(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SEC", count = packStat.sec, countChange = {
				try {
					packStatViewModel.updateSec(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SSP", count = packStat.ssp, countChange = {
				try {
					packStatViewModel.updateSsp(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SP", count = packStat.sp, countChange = {
				try {
					packStatViewModel.updateSp(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "SR", count = packStat.sr, countChange = {
				try {
					packStatViewModel.updateSr(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "Set Rarity", count = packStat.setRarity, countChange = {
				try {
					packStatViewModel.updateSetRarity(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "RRR+", count = packStat.rrrPlus, countChange = {
				try {
					packStatViewModel.updateRrrPlus(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "RRR", count = packStat.rrr, countChange = {
				try {
					packStatViewModel.updateRrr(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "RR", count = packStat.rr, countChange = {
				try {
					packStatViewModel.updateRr(it)
				} catch (e: Exception) {
				}
			})
			RarityCounterTile(label = "R", count = packStat.r, countChange = {
				try {
					packStatViewModel.updateR(it)
				} catch (e: Exception) {
				}
			})
		}

		Text(
			text = "Click tile to input number",
			modifier = Modifier.padding(10.dp),
			color = Color.Gray
		)
	}
}

@Composable
fun RarityCounterTile(
	label: String,
	count: Int,
	countChange: (Int) -> Unit,
) {

	var showInputDialog by remember { mutableStateOf(false) }

	Box {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(label, style = MaterialTheme.typography.labelMedium)

			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier =
					Modifier
						.padding(2.dp)
						.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
						.padding(horizontal = 4.dp, vertical = 4.dp),
			) {
				Button(
					onClick = { if (count > 0) countChange(count - 1) },
					contentPadding = PaddingValues(0.dp),
					modifier = Modifier.size(32.dp),
				) {
					Text("-")
				}

				Text(
					text = count.toString(),
					modifier = Modifier
						.padding(horizontal = 16.dp)
						.clickable { showInputDialog = true },
					style = MaterialTheme.typography.bodyMedium,
				)

				Button(
					onClick = { countChange(count + 1) },
					contentPadding = PaddingValues(0.dp),
					modifier = Modifier.size(32.dp),
				) {
					Text("+")
				}
			}
		}
	}

	if (showInputDialog) {
		var textInput by remember { mutableStateOf(count.toString()) }

		AlertDialog(
			onDismissRequest = { showInputDialog = false },
			title = { Text("Set $label count") },
			text = {
				OutlinedTextField(
					value = textInput,
					onValueChange = { textInput = it.filter { it.isDigit() } },
					singleLine = true,
					label = { Text("New Count") }
				)
			},
			confirmButton = {
				Button(onClick = {
					val newCount = textInput.toIntOrNull() ?: count
					countChange(newCount)
					showInputDialog = false
				}) {
					Text("OK")
				}
			},
			dismissButton = {
				Button(onClick = { showInputDialog = false }) {
					Text("Cancel")
				}
			}
		)
	}
}

@Composable
fun PackNumInputField(packStatViewModel: PackStatViewModel) {
	val packStat by packStatViewModel.packStat.collectAsState()

	var packsDisplay by remember { mutableStateOf("") }
	var cardsPerPackDisplay by remember { mutableStateOf("") }

	LaunchedEffect(packStat.packs) {
		packsDisplay = if (packStat.packs == 0) "" else packStat.packs.toString()
	}

	LaunchedEffect(packStat.cardsPerPack) {
		cardsPerPackDisplay = if (packStat.cardsPerPack == 0) "" else packStat.cardsPerPack.toString()
	}

	Row {
		OutlinedTextField(
			label = { Text("Packs") },
			value = packsDisplay,
			onValueChange = {
				val filtered = it.filter { it.isDigit() }
				packsDisplay = filtered
				try {
					if (packsDisplay.isNotEmpty()) {
						packStatViewModel.updatePacks(packsDisplay.toInt())
					}
				} catch (e: Exception) {
				}
			},
			modifier =
				Modifier
					.weight(1f)
					.padding(10.dp),
		)

		OutlinedTextField(
			label = { Text("Cards Per Pack") },
			value = cardsPerPackDisplay,
			onValueChange = {
				val filtered = it.filter { it.isDigit() }
				cardsPerPackDisplay = filtered
				try {
					if (cardsPerPackDisplay.isNotEmpty()) {
						packStatViewModel.updateCardsPerPack(cardsPerPackDisplay.toInt())
					}
				} catch (e: Exception) {
				}
			},
			modifier =
				Modifier
					.weight(1f)
					.padding(10.dp),
		)
	}
}
