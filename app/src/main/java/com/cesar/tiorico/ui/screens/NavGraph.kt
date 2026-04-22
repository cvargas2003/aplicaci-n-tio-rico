package com.cesar.tiorico.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.cesar.tiorico.ui.screens.*
import com.cesar.tiorico.viewmodel.GameViewModel

// 🎯 RUTAS CENTRALIZADAS (PRO)
object Routes {
    const val LOGIN = "login"
    const val META = "meta"
    const val GAME = "game"
    const val RESULT = "result"
}

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // 🔐 LOGIN
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.META) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 🎯 META
        composable(Routes.META) {
            MetaScreen(
                viewModel = viewModel,
                onStartGame = {
                    navController.navigate(Routes.GAME)
                }
            )
        }

        // 🎮 GAME
        composable(Routes.GAME) {

            GameScreen(viewModel = viewModel)

            // 🔥 NAVEGACIÓN SEGURA (IMPORTANTE)
            LaunchedEffect(viewModel.state.juegoTerminado) {
                if (viewModel.state.juegoTerminado) {
                    navController.navigate(Routes.RESULT)
                }
            }
        }

        // 🏆 RESULTADO
        composable(Routes.RESULT) {
            ResultScreen(
                viewModel = viewModel,
                onRestart = {
                    viewModel.reiniciarJuego()
                    navController.navigate(Routes.META) {
                        popUpTo(Routes.META) { inclusive = true }
                    }
                },
                onExit = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}