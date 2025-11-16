package edu.victoraracil.prclase03.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentsAppUI(
    onOpenMap: () -> Unit, onOpenWeb: () -> Unit, onDialPhone: () -> Unit, onOpenCamera: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PR-CLASE-03", fontSize = 28.sp
                    )
                })
        }) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .background(Color(0xFFF7EFFA)), // color fondo tipo rosado suave
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PillButton(
                text = "Abrir una ubicación en el mapa",
                icon = Icons.Default.Place,
                onClick = onOpenMap
            )

            Spacer(Modifier.height(16.dp))

            PillButton(
                text = "Abrir la web del instituto",
                icon = Icons.Default.Search,
                onClick = onOpenWeb
            )

            Spacer(Modifier.height(16.dp))

            PillButton(
                text = "Marcar un número de teléfono",
                icon = Icons.Default.Phone,
                onClick = onDialPhone
            )

            Spacer(Modifier.height(16.dp))

            PillButton(
                text = "Abrir la cámara de fotos",
                icon = Icons.Default.Person,
                onClick = onOpenCamera
            )
        }
    }
}

@Composable
fun PillButton(
    text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEDE1F9), contentColor = Color(0xFF7A4DA0)
        )
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF7A4DA0))
        Spacer(Modifier.width(12.dp))
        Text(text)
    }
}