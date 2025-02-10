package jeonghwan.app.gpa.ui.screen.navi

sealed class NaviItems(val route: String, val title: String)
{
    companion object {
        const val MAP = "MAP"
        const val PROFILE = "PROFILE"
        const val SETTINGS = "SETTINGS"
    }
    data object Home : NaviItems("home", MAP)
    data object Profile : NaviItems("profile", PROFILE)
    data object Settings : NaviItems("settings", SETTINGS)
}