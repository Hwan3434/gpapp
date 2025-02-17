package jeonghwan.app.gpa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import jeonghwan.app.gpa.ui.screen.Router
import jeonghwan.app.gpa.ui.theme.GpaTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GpaTheme {
                Router(modifier = Modifier)
            }
        }
    }
}
