package com.gymgen.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Objeto singleton que configura e fornece a instância do Retrofit
 * Responsável pela criação do cliente HTTP e configuração da comunicação com a API
 */
object RetrofitClient {
    
    // URL base do backend - IMPORTANTE: Alterar para o IP da máquina que roda o servidor Flask
    private const val BASE_URL = "http://192.168.1.100:5000/"
    
    /**
     * Configura o cliente HTTP com interceptores e timeouts
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    /**
     * Configura o Gson para serialização/deserialização JSON
     */
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    
    /**
     * Instância configurada do Retrofit
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    
    /**
     * Propriedade que expõe o serviço da API
     */
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}