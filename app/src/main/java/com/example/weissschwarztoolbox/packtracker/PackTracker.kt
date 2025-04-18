package com.example.weissschwarztoolbox.packtracker

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch
import kotlin.reflect.full.memberProperties

@Composable
fun PackTracker(navController: NavController){

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                onItemClick = {
                    coroutineScope.launch { drawerState.close() }
                })
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
                    title = "Pack Tracker"
                )
            }
        ) { padding ->
            PackStatContent(paddings = padding)
        }
    }
}


@Composable
fun PackStatContent(paddings: PaddingValues) {

    val options = listOf("2024-04-10", "2024-04-22", "2024-04-30")
    var selected by remember { mutableStateOf<String?>(null) }



    Column(modifier = Modifier
        .padding(paddings)
        .fillMaxSize()) {

//        PackHistorySelector(options, selected, onOptionSelected = { selected = it })

        PackInputFields()

        PackStats()
    }
}

@Composable
fun PackStats() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Text(text = "Pack Stats", color = MaterialTheme.colorScheme.onPrimary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackHistorySelector(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            readOnly = true,
            value = selectedOption ?: "",
            onValueChange = {},
            label = { Text("Select Record") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onOptionSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onClick = {}) {
            Text("new")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onClick = {}) {
            Text("save")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onClick = {}) {
            Text("load")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onClick = {},
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("delete")
        }
    }
}


@Composable
fun PackInputFields() {

    val context = LocalContext.current
    val packStat = remember { mutableStateOf(PackStatStorage.load(context)) }


    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp)
            )
    ) {


        // input field
        Column(modifier = Modifier.padding(10.dp)) {


            // pack input
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color = Color.Green)
            ) {

                PackNumInputField()

            }


            // rarity input
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
            ) {

                PackRarityInputField()
            }


        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PackRarityInputField() {

    val excluded = listOf("date", "packs", "cardsPerPack")
    val rarityList = PackStat::class.memberProperties.map { it.name }.filterNot { it in excluded }



    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
    ) {

        rarityList.forEach { rarity ->

            RarityCounterTile(
                label = rarity,
                count = 0,
                countChange = {}
            )
        }

    }
}

@Composable
fun RarityCounterTile(
    label: String,
    count: Int,
    countChange: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(label, style = MaterialTheme.typography.labelMedium)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Button(
                onClick = { if (count > 0) countChange(count - 1) },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(32.dp)
            ) {
                Text("-")
            }

            Text(
                text = count.toString(),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = { countChange(count + 1) },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(32.dp)
            ) {
                Text("+")
            }
        }
    }
}


@Composable
fun PackNumInputField() {
    Row() {
        OutlinedTextField(
            label = { Text("Packs") },
            value = "",
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )

        OutlinedTextField(
            label = { Text("Cards Per Pack") },
            value = "",
            onValueChange = {},
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
    }
}