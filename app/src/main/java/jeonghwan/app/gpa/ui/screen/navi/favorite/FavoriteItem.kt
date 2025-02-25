package jeonghwan.app.gpa.ui.screen.navi.favorite

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jeonghwan.app.entity.PersonEntity

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    person: PersonEntity,
) {
    Card {
        Text(text = person.name)
    }
}
