package jeonghwan.app.gpa.ui.screen

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import jeonghwan.app.gpa.ui.screen.main.MainScreen
import jeonghwan.app.gpa.ui.screen.navi.detail.PersonDetailScreen
import timber.log.Timber

sealed class RouterItems(val route: String) {
    data object MAIN : RouterItems("main")
    data object PERSON_DETAIL : RouterItems("personDetail"){
        const val parameter = "personKey"
        val path = "$route/{$parameter}"
        fun createPath(personKey: Int) = "$route/$personKey"
    }
}

@Composable
fun Router(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RouterItems.MAIN.route,
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        enterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(route = RouterItems.MAIN.route) {
            MainScreen(
                modifier = modifier,
                onNextButtonClicked = { personKey ->
                    Timber.d("화면 전환 요청 : $personKey : ${RouterItems.PERSON_DETAIL.createPath(personKey)}")
                    navController.navigate(RouterItems.PERSON_DETAIL.createPath(personKey))
                },
                onFavoriteButtonClicked = { personKey ->
                    TODO("Not yet implemented")
                }
            )
        }

        composable(
            route = RouterItems.PERSON_DETAIL.path,
            arguments = listOf(
                navArgument(RouterItems.PERSON_DETAIL.parameter) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
        ) { backStackEntry ->
            val personKey = backStackEntry.arguments!!.getInt(RouterItems.PERSON_DETAIL.parameter)
            PersonDetailScreen(
                modifier = modifier,
                personKey = personKey,
                onFavoriteButtonClicked = { personKey ->
                    TODO("Not yet implemented")
                }
            )
        }
    }
}
