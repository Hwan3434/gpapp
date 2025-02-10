package jeonghwan.app.gpa.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import jeonghwan.app.gpa.ui.screen.navi.favorite.FavoritePersonScreen
import jeonghwan.app.gpa.ui.screen.navi.map.MapScreen
import jeonghwan.app.gpa.ui.screen.navi.person.PersonListScreen


@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (Int) -> Unit
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Favorites", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Person)

    Scaffold(
        bottomBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                tabs = {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            icon = { Icon(icons[index], contentDescription = null) },
                            text = { Text(title) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }
            )
        }
    )  { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            when (selectedTabIndex) {
                0 -> MapScreen()
                1 -> PersonListScreen(onNextButtonClicked = onNextButtonClicked)
                2 -> FavoritePersonScreen()
            }
        }
    }
}