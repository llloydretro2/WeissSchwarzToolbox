package com.example.weissschwarztoolbox.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.weissschwarztoolbox.component.TopBarComponent
import com.example.weissschwarztoolbox.component.DrawerContent
import kotlinx.coroutines.launch



@Composable
fun HomeScreen(
    navController: NavController) {

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
                    title = "Weiss Schwarz Toolbox"
                )
            }
        ) { padding ->
            Text("Home Screen", modifier = Modifier.padding(padding))
        }
    }


}


