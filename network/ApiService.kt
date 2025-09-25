package com.gymgen.network

import com.gymgen.models.WorkoutPlanResponse
import com.gymgen.models.WorkoutRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interface que define os endpoints da API para comunicação com o backend GymGen
 * Utiliza Retrofit para simplificar as chamadas HTTP
 */
interface ApiService {
    
    /**
     * Endpoint para gerar um plano de treino personalizado
     * @param request Objeto WorkoutRequest contendo objetivo e nível de experiência
     * @return WorkoutPlanResponse contendo o plano semanal de treinos
     */
    @POST("api/generate-workout")
    suspend fun generateWorkout(
        @Body request: WorkoutRequest
    ): WorkoutPlanResponse
}