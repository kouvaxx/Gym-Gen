package com.gymgen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gymgen.models.WorkoutDay
import com.gymgen.models.WorkoutPlanResponse
import com.gymgen.ui.components.WorkoutDayCard
import com.gymgen.viewmodel.WorkoutViewModel

/**
 * Tela que exibe o plano de treino gerado
 * Mostra os dias de treino e exercícios em uma lista scrollável
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanScreen(
    viewModel: WorkoutViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onTryAgain: () -> Unit
) {
    // Observa o estado do ViewModel
    val uiState = viewModel.uiState.value
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Seu Plano Semanal",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is com.gymgen.viewmodel.UiState.Success -> {
                // Exibe o plano de treino em caso de sucesso
                WorkoutPlanContent(
                    workoutPlan = uiState.data,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            is com.gymgen.viewmodel.UiState.Error -> {
                // Exibe mensagem de erro em caso de falha
                ErrorContent(
                    message = uiState.message,
                    onTryAgain = onTryAgain,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                // Estados iniciais ou loading não devem ser exibidos aqui
                // A navegação deve garantir que só Success ou Error cheguem aqui
            }
        }
    }
}

/**
 * Componente que exibe o conteúdo do plano de treino
 */
@Composable
private fun WorkoutPlanContent(
    workoutPlan: WorkoutPlanResponse,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header informativo
        item {
            Text(
                text = "Seu plano de treino personalizado está pronto!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }
        
        // Lista de dias de treino
        items(workoutPlan.plano_semanal) { workoutDay ->
            WorkoutDayCard(workoutDay = workoutDay)
        }
        
        // Footer com informações adicionais
        item {
            Text(
                text = "Lembre-se de aquecer antes e alongar após os treinos.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

/**
 * Componente que exibe mensagem de erro
 */
@Composable
private fun ErrorContent(
    message: String,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ops! Algo deu errado",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Button(
            onClick = onTryAgain,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tentar Novamente")
        }
    }
}