package com.example.weissschwarztoolbox.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DrawerContent(
    navController: NavController,
    onItemClick: () -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.primary,
        drawerContentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Box(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Companion.Center,
        ) {
            Text(
                text = "Functions",
                modifier = Modifier.Companion.padding(16.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            )

        }

        // Power Indicator
        NavigationDrawerItem(
            label = {
                Text(
                    text = "🔥 Power Indicator",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            selected = false,
            onClick = {
                onItemClick()
                navController.navigate("powerIndicator") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            shape = RectangleShape

        )

        // Chess Clock
        NavigationDrawerItem(
            label = {
                Text(
                    text = "♟\uFE0F Chess Clock",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            selected = false,
            onClick = {
                onItemClick()
                navController.navigate("chessClock") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            shape = RectangleShape,

            )

        // Timer
        NavigationDrawerItem(
            label = {
                Text(
                    text = "⏰ Timer",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            selected = false,
            onClick = {
                onItemClick()
                navController.navigate("timer") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            shape = RectangleShape,


            )


        // first/second
        NavigationDrawerItem(
            label = {
                Text(
                    text = "1️⃣ First/Second",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            selected = false,
            onClick = {
                onItemClick()
                navController.navigate("firstSecond") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            shape = RectangleShape,


            )




        // win/loss tracker
        NavigationDrawerItem(
            label = {
                Text(
                    text = "🥇 Win/Loss Tracker",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            },
            selected = false,
            onClick = {
                onItemClick()
                navController.navigate("winLossTracker") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            shape = RectangleShape,


            )


        // TODO calculator maybe?

    }

}