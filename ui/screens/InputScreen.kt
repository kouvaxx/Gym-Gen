package com.gymgen.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gymgen.viewmodel.WorkoutViewModel

/**
 * Tela de entrada onde o usuário seleciona suas preferências de treino
 * Coleta objetivo e nível de experiência para enviar à API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    viewModel: WorkoutViewModel = viewModel(),
    onNavigateToLoading: () -> Unit
) {
    // Estados para os dropdowns
    var objetivoExpanded by remember { mutableStateOf(false) }
    var nivelExpanded by remember { mutableStateOf(false) }
    
    // Valores selecionados
    var selectedObjetivo by remember { mutableStateOf("") }
    var selectedNivel by remember { mutableStateOf("") }
    
    // Opções para os dropdowns
    val objetivoOptions = listOf("Ganho de Massa (Hipertrofia)", "Perda de Peso")
    val nivelOptions = listOf("Iniciante", "Intermediário", "Avançado")
    
    // Verifica se ambos os campos foram preenchidos
    val isButtonEnabled = selectedObjetivo.isNotEmpty() && selectedNivel.isNotEmpty()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título do app
        Text(
            text = "GymGen",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        // Dropdown para Objetivo
        ExposedDropdownMenuBox(
            expanded = objetivoExpanded,
            onExpandedChange = { objetivoExpanded = !objetivoExpanded }
        ) {
            OutlinedTextField(
                value = selectedObjetivo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Objetivo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = objetivoExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = objetivoExpanded,
                onDismissRequest = { objetivoExpanded = false }
            ) {
                objetivoOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedObjetivo = option
                            objetivoExpanded = false
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Dropdown para Nível de Experiência
        ExposedDropdownMenuBox(
            expanded = nivelExpanded,
            onExpandedChange = { nivelExpanded = !nivelExpanded }
        ) {
            OutlinedTextField(
                value = selectedNivel,
                onValueChange = {},
                readOnly = true,
                label = { Text("Nível de Experiência") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = nivelExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            
            ExposedDropdownMenu(
                expanded = nivelExpanded,
                onDismissRequest = { nivelExpanded = false }
            ) {
                nivelOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedNivel = option
                            nivelExpanded = false
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Botão para gerar treino
        Button(
            onClick = {
                // Mapeia os valores para o formato da API
                val objetivoAPI = viewModel.mapObjetivo(selectedObjetivo)
                val nivelAPI = viewModel.mapNivelExperiencia(selectedNivel)
                
                // Chama o ViewModel para processar
                viewModel.generateWorkout(objetivoAPI, nivelAPI)
                
                // Navega para tela de loading
                onNavigateToLoading()
            },
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Gerar Meu Treino",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        // Mensagem de orientação quando o botão está desabilitado
        if (!isButtonEnabled) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Por favor, selecione ambos os campos para continuar",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}