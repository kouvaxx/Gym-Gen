package com.gymgen.data

import com.gymgen.models.WorkoutPlanResponse
import com.gymgen.models.WorkoutRequest
import com.gymgen.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository que gerencia os dados de treino
 * Segue o padrão Repository para abstrair a fonte de dados (API, cache, etc.)
 * É a única fonte de verdade para os dados de treino
 */
class WorkoutRepository {
    
    /**
     * Função suspend que busca o plano de treino da API
     * @param objetivo Objetivo do treino ("hipertrofia" ou "perder_peso")
     * @param nivelExperiencia Nível de experiência do usuário
     * @return Resultado contendo WorkoutPlanResponse em caso de sucesso ou exceção em caso de erro
     */
    suspend fun getWorkoutPlan(objetivo: String, nivelExperiencia: String): Result<WorkoutPlanResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Cria o objeto de requisição
                val request = WorkoutRequest(
                    objetivo = objetivo,
                    nivel_experiencia = nivelExperiencia
                )
                
                // Faz a chamada à API
                val response = RetrofitClient.apiService.generateWorkout(request)
                
                // Retorna sucesso com os dados
                Result.success(response)
                
            } catch (e: Exception) {
                // Em caso de erro, retorna falha com a exceção
                // Isso permite que o ViewModel trate diferentes tipos de erro
                Result.failure(e)
            }
        }
    }
}