package edu.javiercarrasco.coffeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.javiercarrasco.coffeeapp.ui.navigation.AppNavigation
import edu.javiercarrasco.coffeeapp.ui.theme.CoffeeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeeAppTheme {
                AppNavigation()
            }
        }
    }
}