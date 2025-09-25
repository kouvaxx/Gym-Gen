package com.gymgen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gymgen.ui.screens.InputScreen
import com.gymgen.ui.screens.LoadingScreen
import com.gymgen.ui.screens.WorkoutPlanScreen
import com.gymgen.viewmodel.WorkoutViewModel
import com.gymgen.viewmodel.UiState

/**
 * Define as rotas (destinos) da navegação
 * Usando sealed class para type safety
 */
sealed class Screen(val route: String) {
    object Input : Screen("input")
    object Loading : Screen("loading")
    object WorkoutPlan : Screen("workout_plan")
}

/**
 * Configuração do gráfico de navegação
 * Define como as telas se conectam e transicionam
 */
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: WorkoutViewModel = viewModel()
) {
    // Observa o estado do ViewModel para navegação reativa
    val uiState by viewModel.uiState.collectAsState()
    
    // Efeito que observa mudanças no estado e navega automaticamente
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Initial -> {
                // Garante que estamos na tela inicial
                if (navController.currentBackStackEntry?.destination?.route != Screen.Input.route) {
                    navController.popBackStack(Screen.Input.route, inclusive = false)
                }
            }
            is UiState.Loading -> {
                // Navega para tela de loading
                navController.navigate(Screen.Loading.route) {
                    popUpTo(Screen.Input.route) { inclusive = false }
                }
            }
            is UiState.Success, is UiState.Error -> {
                // Navega para tela de resultado (sucesso ou erro)
                navController.navigate(Screen.WorkoutPlan.route) {
                    popUpTo(Screen.Loading.route) { inclusive = true }
                }
            }
        }
    }
    
    // Configuração do NavHost com as rotas
    NavHost(
        navController = navController,
        startDestination = Screen.Input.route
    ) {
        // Tela de entrada (input)
        composable(Screen.Input.route) {
            InputScreen(
                viewModel = viewModel,
                onNavigateToLoading = {
                    // A navegação é tratada pelo LaunchedEffect acima
                    // Mas mantemos o callback para manter a interface da função
                }
            )
        }
        
        // Tela de carregamento
        composable(Screen.Loading.route) {
            LoadingScreen()
        }
        
        // Tela de plano de treino
        composable(Screen.WorkoutPlan.route) {
            WorkoutPlanScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    // Volta para tela inicial e reseta o estado
                    viewModel.resetState()
                    navController.popBackStack(Screen.Input.route, inclusive = false)
                },
                onTryAgain = {
                    // Volta para tela inicial para tentar novamente
                    viewModel.resetState()
                    navController.popBackStack(Screen.Input.route, inclusive = false)
                }
            )
        }
    }
}