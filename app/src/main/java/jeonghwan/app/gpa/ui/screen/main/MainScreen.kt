package jeonghwan.app.gpa.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.naver.maps.map.compose.CameraPositionState
import jeonghwan.app.gpa.R
import jeonghwan.app.gpa.ui.screen.navi.favorite.FavoritePersonScreen
import jeonghwan.app.gpa.ui.screen.navi.map.MapScreen
import jeonghwan.app.gpa.ui.screen.navi.person.ProxyPersonListScreen

sealed class NaviItems(val route: String, private val titleResId: Int, val icon: ImageVector) {
    companion object {
        const val MAP = "MAP"
        const val PERSON = "PERSON"
        const val FAVORITE = "FAVORITE"
    }

    data object Map : NaviItems(MAP, R.string.navi_map, Icons.Filled.LocationOn)

    data object Person : NaviItems(PERSON, R.string.navi_person, Icons.Filled.Person)

    data object Favorite : NaviItems(FAVORITE, R.string.navi_favorite, Icons.Filled.Favorite)

    @Composable
    fun GetTab(
        selected: NaviItems,
        onClick: () -> Unit,
    ) {
        Tab(
            icon = { Icon(this.icon, contentDescription = null) },
            text = { Text(stringResource(this.titleResId)) },
            selected = selected == this,
            onClick = onClick,
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    selectedTab: NaviItems,
    cameraPositionState: CameraPositionState,
    goToPersonDetail: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
    onTab: (NaviItems) -> Unit,
) {
    val tabs = listOf(NaviItems.Map, NaviItems.Person, NaviItems.Favorite)
    val saveableStateHolder = rememberSaveableStateHolder()

    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = tabs.indexOf(selectedTab),
                tabs = {
                    tabs.forEach { tab ->
                        tab.GetTab(
                            selectedTab,
                        ) {
                            onTab(tab)
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            saveableStateHolder.SaveableStateProvider(key = selectedTab.route) {
                when (selectedTab) {
                    NaviItems.Map ->
                        MapScreen(
                            cameraPositionState = cameraPositionState,
                            goToPersonDetail = goToPersonDetail,
                        )
                    NaviItems.Person ->
                        ProxyPersonListScreen(
                            goToPersonDetail = goToPersonDetail,
                        )
                    NaviItems.Favorite -> FavoritePersonScreen()
                }
            }
        }
    }
}
