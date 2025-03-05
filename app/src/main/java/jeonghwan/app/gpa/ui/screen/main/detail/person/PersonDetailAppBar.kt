package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import jeonghwan.app.domain.model.PersonEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailAppBar(
    personEntity: PersonEntity?,
    close: () -> Unit,
    icons: @Composable RowScope.(PersonEntity) -> Unit,
) {
    TopAppBar(
        title = {
            TitleText(personEntity)
        },
        navigationIcon = {
            IconButton(onClick = close) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            personEntity?.let {
                icons(it)
            }
        },
    )
}

@Composable
private fun TitleText(person: PersonEntity?) {
    Text(person?.name ?: "Loading...")
}

@Composable
fun Location(
    key: Int,
    goToMap: (Int) -> Unit,
) {
    IconButton(onClick = {
        if (key != 0) {
            goToMap(key)
        }
    }) {
        Icon(Icons.Default.LocationOn, contentDescription = "Back")
    }
}

@Composable
fun FavoriteIcon(
    person: PersonEntity,
    isFavorite: Boolean,
    onFavoriteCheckedChange: (Int) -> Unit,
) {
    IconButton(onClick = {
        if (person.key != 0) {
            onFavoriteCheckedChange(person.key)
        }
    }) {
        Icon(if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = "Back")
    }
}
