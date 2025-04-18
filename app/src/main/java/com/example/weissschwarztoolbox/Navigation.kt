package com.example.weissschwarztoolbox

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weissschwarztoolbox.chessclock.ChessClock
import com.example.weissschwarztoolbox.damagecalculator.DamageCalculator
import com.example.weissschwarztoolbox.firstsecond.FirstSecond
import com.example.weissschwarztoolbox.home.HomeScreen
import com.example.weissschwarztoolbox.packtracker.PackTracker
import com.example.weissschwarztoolbox.powerindicator.PowerIndicator
import com.example.weissschwarztoolbox.timer.Timer
import com.example.weissschwarztoolbox.winlosstracker.WinLossTracker

@Composable
fun Navigation() {

    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "packTracker") {

        composable("home") {
            HomeScreen(navController)
        }

        composable("powerIndicator") {
            PowerIndicator(navController)
        }

        composable("chessClock") {
            ChessClock(navController)
        }

        composable("timer") {
            Timer(navController)
        }

        composable("firstSecond") {
            FirstSecond(navController)
        }

        composable("winLossTracker") {
            WinLossTracker(navController)
        }

        composable("packTracker") {
            PackTracker(navController)
        }

        composable("damageCalculator") {
            DamageCalculator(navController)
        }

    }
}