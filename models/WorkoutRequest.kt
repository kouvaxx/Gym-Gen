package com.gymgen.models

/**
 * Data class para enviar a requisição de geração de treino para a API
 * @property objetivo Objetivo do treino ("hipertrofia" ou "perder_peso")
 * @property nivel_experiencia Nível de experiência do usuário ("Iniciante", "Intermediário", "Avançado")
 */
data class WorkoutRequest(
    val objetivo: String,
    @SerializedName("nivel_experiencia")
    val nivel_experiencia: String
)