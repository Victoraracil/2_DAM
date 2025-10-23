package edu.victoraracil.ej0101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.victoraracil.ej0101.ui.theme.Ejercicio_Practico0101Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio_Practico0101Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RememberVsMutableStateDemo(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun RememberVsMutableStateDemo(modifier: Modifier = Modifier) {
        Surface(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                // 1. Cambia y la UI se actualiza.
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Titulo",
                            style = MaterialTheme.typography.titleMedium
                        )
                        var checked by remember { mutableStateOf(false) }
                        Text(
                            text = "Subtitulo"
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Switch(
                                checked = checked,
                                onCheckedChange = { checked = it }
                            )
                            Text(
                                if (checked) "En Favoritos" else "No en Favoritos"
                            )
                        }

                    }
                }

                // 2. Valor recordado NO observable: no hay recomposición al reasignar la variable local.
                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Valor recordado sin estado observable",
                            style = MaterialTheme.typography.titleMedium
                        )

                        // OJO: esto recuerda "false" una vez, pero NO es State<T>.
                        var naive = remember { false }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // La UI LEE 'naive' pero cambiarlo no provoca recomposición.
                            Switch(
                                checked = naive,
                                onCheckedChange = { newValue ->
                                    // Esto cambia la variable local, pero NO avisa a Compose.
                                    naive = newValue
                                    println("Desde el Switch: $naive")
                                }
                            )
                            Text(
                                if (naive) "Valor leído: TRUE (no observable)" else "Valor leído: FALSE (no observable)"
                            )
                        }

                        Button(
                            onClick = {
                                // También “cambia” la variable local, la UI no se ve afectada.
                                naive = !naive
                                println("Desde el Button: $naive")
                            }
                        ) {
                            Text("Intentar alternar (no actualizará la UI)")
                        }

                        Text(
                            text = "Este bloque usa un Boolean 'recordado' pero no observable. " +
                                    "Reasignarlo no dispara recomposición, por lo que la UI no refleja los cambios."
                        )
                    }
                }

                //Opcional
                AssistChip(
                    onClick = {},
                    label = { Text("Consejo: si necesitas UI reactiva, usa State<T> (p. ej., mutableStateOf).") }
                )
            }
        }
    }
}