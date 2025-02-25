package jeonghwan.app.gpa.ui.screen.navi.favorite

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import jeonghwan.app.entity.PersonEntity

@Composable
fun FavoriteItem(person: PersonEntity) {
    Card {
        Text(text = person.name)
    }
}
