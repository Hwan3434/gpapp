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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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

    data object Map : NaviItems(MAP, R.string.navi_map, Icons.Filled.LocationOn) {
        @Composable
        override fun getScreen(
            modifier: Modifier,
            onNextButtonClicked: ((Int) -> Unit)?,
            onFavoriteButtonClicked: ((Int) -> Unit)?,
        ) {
            MapScreen(
                onNextButtonClicked = onNextButtonClicked!!,
            )
        }
    }

    data object Person : NaviItems(PERSON, R.string.navi_person, Icons.Filled.Person) {
        @Composable
        override fun getScreen(
            modifier: Modifier,
            onNextButtonClicked: ((Int) -> Unit)?,
            onFavoriteButtonClicked: ((Int) -> Unit)?,
        ) {
            assert(onNextButtonClicked != null)
            assert(onFavoriteButtonClicked != null)
            ProxyPersonListScreen(
                onDetailButtonClicked = onNextButtonClicked!!,
                onFavoriteButtonClicked = onFavoriteButtonClicked!!,
            )
        }
    }

    data object Favorite : NaviItems(FAVORITE, R.string.navi_favorite, Icons.Filled.Favorite) {
        @Composable
        override fun getScreen(
            modifier: Modifier,
            onNextButtonClicked: ((Int) -> Unit)?,
            onFavoriteButtonClicked: ((Int) -> Unit)?,
        ) {
            FavoritePersonScreen()
        }
    }

    @Composable
    abstract fun getScreen(
        modifier: Modifier,
        onNextButtonClicked: ((Int) -> Unit)?,
        onFavoriteButtonClicked: ((Int) -> Unit)?,
    )

    @Composable
    fun getTab(
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

private val naviItemsSaver =
    Saver<NaviItems, String>(
        save = { it.route },
        restore = { route ->
            val tabs = listOf(NaviItems.Map, NaviItems.Person, NaviItems.Favorite)
            tabs.find { it.route == route } ?: NaviItems.Map
        },
    )

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
) {
    var selectedTab by rememberSaveable(stateSaver = naviItemsSaver) { mutableStateOf(NaviItems.Map) }
    val tabs = listOf(NaviItems.Map, NaviItems.Person, NaviItems.Favorite)

    val saveableStateHolder = rememberSaveableStateHolder()

    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = tabs.indexOf(selectedTab),
                tabs = {
                    tabs.forEach { tab ->
                        tab.getTab(
                            selectedTab,
                        ) {
                            selectedTab = tab
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            saveableStateHolder.SaveableStateProvider(key = selectedTab.route) {
                selectedTab.getScreen(
                    modifier,
                    onNextButtonClicked,
                    onFavoriteButtonClicked,
                )
            }
        }
    }
}
