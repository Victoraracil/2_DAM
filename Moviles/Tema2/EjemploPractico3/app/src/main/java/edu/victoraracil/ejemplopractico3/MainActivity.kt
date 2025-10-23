package edu.victoraracil.ejemplopractico3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.victoraracil.ejemplopractico3.ui.theme.EjemploPractico3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploPractico3Theme {
                VistaPreviaPantallaAncha()
                VistaPreviaPantallaEstrecha()

            }
        }
    }
}
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun EjemploBoxWithConstraints() {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray)
    ) {
        val isPantallaGrande = maxWidth > 300.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isPantallaGrande) Color.Cyan else Color.Magenta),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isPantallaGrande) "Pantalla ancha" else "Pantalla estrecha",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
@Preview(showBackground = true, widthDp = 400)
@Composable
fun VistaPreviaPantallaAncha() {
    EjemploBoxWithConstraints()
}

@Preview(showBackground = true, widthDp = 250)
@Composable
fun VistaPreviaPantallaEstrecha() {
    EjemploBoxWithConstraints()
}