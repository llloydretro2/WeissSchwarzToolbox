package com.example.weissschwarztoolbox

import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weissschwarztoolbox.chessclock.ChessClock
import com.example.weissschwarztoolbox.chessclock.ChessClockViewModel
import com.example.weissschwarztoolbox.choosepack.ChoosePack
import com.example.weissschwarztoolbox.damagecalculator.DamageCalculator
import com.example.weissschwarztoolbox.firstsecond.FirstSecond
import com.example.weissschwarztoolbox.home.HomeScreen
import com.example.weissschwarztoolbox.packtracker.PackTracker
import com.example.weissschwarztoolbox.powerindicator.PowerIndicator
import com.example.weissschwarztoolbox.timer.Timer
import com.example.weissschwarztoolbox.winlosstracker.winLossTracker
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
	val navController = rememberNavController()
	val currentBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = currentBackStackEntry?.destination?.route
	val chessClockViewModel: ChessClockViewModel = viewModel()


	val isHome = currentRoute == "home"
	if (isHome) {
		BackPressToExit()
	}

	NavHost(navController = navController, startDestination = "home") {
		composable("home") {
			HomeScreen(navController)
		}

		composable("powerIndicator") {
			PowerIndicator(navController)
		}

		composable("chessClock") {
			ChessClock(navController = navController, chessClockViewModel = chessClockViewModel)
		}

		composable("timer") {
			Timer(navController)
		}

		composable("firstSecond") {
			FirstSecond(navController)
		}

		composable("winLossTracker") {
			winLossTracker(navController)
		}

		composable("packTracker") {
			PackTracker(navController)
		}

		composable("damageCalculator") {
			DamageCalculator(navController)
		}

		composable("choosePack") {
			ChoosePack(navController)
		}
	}
}


@Composable
fun BackPressToExit() {
	val context = LocalContext.current
	var backPressedOnce by remember { mutableStateOf(false) }

	LaunchedEffect(backPressedOnce) {
		delay(2000)
		backPressedOnce = false
	}

	BackHandler {
		if (backPressedOnce) {
			(context as? ComponentActivity)?.finish()
		} else {
			backPressedOnce = true
			Toast.makeText(context, "Back again to exit", Toast.LENGTH_SHORT).show()
		}
	}
}
