package com.example.weissschwarztoolbox.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
	menuClick: () -> Unit,
	title: String,
) {
	TopAppBar(
		title = { Text(title) },
		colors =
			TopAppBarDefaults.topAppBarColors(
				containerColor = MaterialTheme.colorScheme.primary,
				titleContentColor = MaterialTheme.colorScheme.onPrimary,
			),
		navigationIcon = {
			Icon(
				Icons.Default.Menu,
				contentDescription = "Menu",
				modifier =
					Modifier.Companion
						.padding(10.dp)
						.clickable { menuClick() },
			)
		},
	)
}
