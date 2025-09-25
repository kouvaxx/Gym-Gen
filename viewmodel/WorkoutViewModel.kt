package com.gymgen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymgen.data.WorkoutRepository
import com.gymgen.models.WorkoutPlanResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gerencia o estado da UI e a lógica de negócio
 * Segue o padrão MVVM e expõe o estado via StateFlow para reatividade
 */
class WorkoutViewModel : ViewModel() {
    
    private val repository = WorkoutRepository()
    
    // Estado privado mutável do ViewModel
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    
    // Estado público imutável exposto para a UI
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    /**
     * Função que processa a geração do plano de treino
     * @param objetivo Objetivo do treino ("hipertrofia" ou "perder_peso")
     * @param nivelExperiencia Nível de experiência do usuário
     */
    fun generateWorkout(objetivo: String, nivelExperiencia: String) {
        // Define o estado como Loading para indicar que a operação está em andamento
        _uiState.value = UiState.Loading
        
        // Lança uma coroutine no escopo do ViewModel
        viewModelScope.launch {
            try {
                // Chama o repository para buscar os dados
                val result = repository.getWorkoutPlan(objetivo, nivelExperiencia)
                
                // Processa o resultado
                result.onSuccess { workoutPlan ->
                    // Em caso de sucesso, atualiza o estado com os dados
                    _uiState.value = UiState.Success(workoutPlan)
                }.onFailure { exception ->
                    // Em caso de erro, atualiza o estado com mensagem de erro
                    _uiState.value = UiState.Error(
                        message = "Falha ao gerar o treino: ${exception.message ?: "Erro desconhecido"}"
                    )
                }
                
            } catch (e: Exception) {
                // Captura qualquer exceção não tratada
                _uiState.value = UiState.Error(
                    message = "Erro inesperado: ${e.message}"
                )
            }
        }
    }
    
    /**
     * Função para resetar o estado para inicial
     * Útil quando o usuário quer voltar à tela inicial
     */
    fun resetState() {
        _uiState.value = UiState.Initial
    }
    
    /**
     * Função para mapear objetivo da UI para formato da API
     */
    fun mapObjetivo(objetivoUI: String): String {
        return when (objetivoUI) {
            "Ganho de Massa (Hipertrofia)" -> "hipertrofia"
            "Perda de Peso" -> "perder_peso"
            else -> objetivoUI.lowercase()
        }
    }
    
    /**
     * Função para mapear nível de experiência da UI para formato da API
     */
    fun mapNivelExperiencia(nivelUI: String): String {
        return when (nivelUI) {
            "Iniciante", "Intermediário", "Avançado" -> nivelUI
            else -> nivelUI
        }
    }
}

/**
 * Classe sealed que representa os diferentes estados da UI
 * Permite um gerenciamento de estado mais robusto e type-safe
 */
sealed class UiState {
    // Estado inicial quando o app é iniciado
    object Initial : UiState()
    
    // Estado de carregamento durante a chamada da API
    object Loading : UiState()
    
    // Estado de sucesso com os dados do plano de treino
    data class Success(val data: WorkoutPlanResponse) : UiState()
    
    // Estado de erro com mensagem descritiva
    data class Error(val message: String) : UiState()
}