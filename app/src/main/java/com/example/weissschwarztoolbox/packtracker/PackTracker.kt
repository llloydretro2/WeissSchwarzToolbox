package com.example.weissschwarztoolbox.packtracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.DrawerContent
import com.example.weissschwarztoolbox.component.TopBarComponent
import kotlinx.coroutines.launch

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

        PackHistorySelector(options, selected, onOptionSelected = { selected = it })

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
            label = { Text("选择开包记录") },
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
            Text("load")
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onClick = {}) {
            Text("delete")
        }
    }
}


@Composable
fun PackInputFields() {


    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Text(text = "Input Field", color = MaterialTheme.colorScheme.onPrimary)

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color = Color.Green)
            ) {
                Text(text = "Pack Input Field", color = MaterialTheme.colorScheme.onPrimary)
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color = Color.Blue)
            ) {
                Text(text = "Active Input Field", color = MaterialTheme.colorScheme.onPrimary)
            }

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(color = Color.Red)
            ) {
                Text(text = "Inactive Input Field", color = MaterialTheme.colorScheme.onPrimary)
            }

        }

    }
}