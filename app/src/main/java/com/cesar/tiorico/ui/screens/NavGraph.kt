package com.cesar.tiorico.ui.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.cesar.tiorico.ui.screens.*
import com.cesar.tiorico.viewmodel.GameViewModel

// 🎯 RUTAS
object Routes {
    const val LOGIN = "login"
    const val SETUP = "setup" // 🔥 nueva pantalla
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
                    navController.navigate(Routes.SETUP) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // 👥 SETUP JUGADORES
        composable(Routes.SETUP) {
            PlayerSetupScreen(
                viewModel = viewModel,
                onStart = {
                    navController.navigate(Routes.GAME)
                }
            )
        }

        // 🎮 GAME
        composable(Routes.GAME) {

            GameScreen(
                viewModel = viewModel,
                onExit = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )

            // 🔥 CUANDO TERMINA → RESULT
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
                    navController.navigate(Routes.SETUP) {
                        popUpTo(Routes.SETUP) { inclusive = true }
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