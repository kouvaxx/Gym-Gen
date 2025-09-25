package com.gymgen.models

import com.google.gson.annotations.SerializedName

/**
 * Data class que representa a resposta completa do plano de treino da API
 * @property plano_semanal Lista de dias de treino semanais
 */
data class WorkoutPlanResponse(
    @SerializedName("plano_semanal")
    val plano_semanal: List<WorkoutDay>
)

/**
 * Data class que representa um dia de treino
 * @property dia Nome do dia de treino (ex: "A - Peito & Tríceps")
 * @property exercicios Lista de exercícios para este dia
 */
data class WorkoutDay(
    val dia: String,
    val exercicios: List<Exercise>
)

/**
 * Data class que representa um exercício específico
 * @property nome Nome do exercício
 * @property series Número de séries (formato string da API)
 * @property repeticoes Número de repetições (formato string da API)
 * @property descanso Tempo de descanso entre séries
 * @property dicas_seguranca Lista de dicas de segurança para o exercício
 */
data class Exercise(
    val nome: String,
    val series: String,
    val repeticoes: String,
    val descanso: String,
    @SerializedName("dicas_seguranca")
    val dicas_seguranca: List<String>
)