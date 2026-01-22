package edu.victoraracil.pr_clase_07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.victoraracil.pr_clase_07.ui.navigation.AppNavHost
import edu.victoraracil.pr_clase_07.ui.theme.PRCLASE07Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PRCLASE07Theme {
                AppNavHost()
            }
        }
    }
}
