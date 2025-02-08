package jeonghwan.app.gpa.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jeonghwan.app.gpa.ui.screen.main.MainScreen

@Composable
fun Router(
    modifier: Modifier = Modifier,
    ) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Main") {
        composable(route = "Main") {
        }

        composable(route = "PersonDetail") {
            PersonDetailScreen()
        }
    }
}

