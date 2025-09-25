package com.gymgen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gymgen.navigation.NavGraph
import com.gymgen.ui.theme.GymGenTheme

/**
 * Activity principal do aplicativo GymGen
 * Ponto de entrada da aplicação Android
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            GymGenTheme {
                // Container principal com tema do aplicativo
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicia o gráfico de navegação
                    NavGraph()
                }
            }
        }
    }
}