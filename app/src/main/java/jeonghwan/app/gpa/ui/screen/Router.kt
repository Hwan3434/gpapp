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
import jeonghwan.app.gpa.ui.screen.main.mainScreen
import jeonghwan.app.gpa.ui.screen.navi.detail.PersonDetailScreen
import jeonghwan.app.gpa.ui.screen.navi.detail.TombDetailScreen

sealed class RouterItems(val route: String) {
    data object Main : RouterItems("main")

    data object PersonDetail : RouterItems("personDetail") {
        const val PARAM = "personKey"
        val path = "$route/{$PARAM}"

        fun createPath(personKey: Int) = "$route/$personKey"
    }

    data object TombDetail : RouterItems("tombDetail") {
        const val PARAM = "tombKey"
        val path = "$route/{$PARAM}"

        fun createPath(tombKey: Int) = "$route/$tombKey"
    }
}

@Composable
fun Router(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RouterItems.Main.route,
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        enterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(route = RouterItems.Main.route) {
            mainScreen(
                modifier = modifier,
                onNextButtonClicked = { personKey ->
                    navController.navigate(RouterItems.PersonDetail.createPath(personKey))
                },
                onFavoriteButtonClicked = { tombKey ->
                    navController.navigate(RouterItems.TombDetail.createPath(tombKey))
                },
            )
        }

        composable(
            route = RouterItems.PersonDetail.path,
            arguments =
                listOf(
                    navArgument(RouterItems.PersonDetail.PARAM) {
                        type = NavType.IntType
                    },
                ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700),
                )
            },
        ) { backStackEntry ->
            val personKey = backStackEntry.arguments!!.getInt(RouterItems.PersonDetail.PARAM)
            PersonDetailScreen(
                modifier = modifier,
                personKey = personKey,
                onFavoriteButtonClicked = { _ ->
                    TODO("Not yet implemented")
                },
            )
        }

        composable(
            route = RouterItems.TombDetail.path,
            arguments =
                listOf(
                    navArgument(RouterItems.TombDetail.PARAM) {
                        type = NavType.IntType
                    },
                ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700),
                )
            },
        ) { backStackEntry ->
            val tombKey = backStackEntry.arguments!!.getInt(RouterItems.TombDetail.PARAM)
            TombDetailScreen(
                modifier = modifier,
                tombKey = tombKey,
            )
        }
    }
}
