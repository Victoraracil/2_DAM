package edu.victoraracil.ejermplot1_1103

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.currentStateAsState
import edu.victoraracil.ejermplot1_1103.ui.theme.EjermploT1_1103Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjermploT1_1103Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> 10.dp
                    CiclosDeVida()
                }
            }
        }
    }
}

@Composable
fun CiclosDeVida() {
    // Se utiliza lifecycleOwner para observar el ciclo de vida de la actividad o fragmento.
    val lifecycleOwner = LocalLifecycleOwner.current
    // Se obtiene el estado actual del ciclo de vida como un estado Compose.
    val estado = lifecycleOwner.lifecycle.currentStateAsState()

    // Se muestra el estado actual del ciclo de vida.
    Log.d("CiclosDeVida", "Estado del ciclo de vida: ${estado.value}")
    Text(
        text = "Estado del ciclo de vida: ${estado.value}",
        modifier = Modifier.padding(16.dp)
    )
}