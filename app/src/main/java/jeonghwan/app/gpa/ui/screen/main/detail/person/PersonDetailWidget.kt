package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import jeonghwan.app.domain.model.PersonEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PersonDetailWidget(
    person: PersonEntity?,
    father: PersonEntity?,
    mother: PersonEntity?,
    spouse: PersonEntity?,
    children: ImmutableList<PersonEntity>,
    siblings: ImmutableList<PersonEntity>,
    goToPerson: (Int) -> Unit,
) {
    Column {
        Text(
            text = "Person Detail Screen: ${person?.name}",
        )
        LinkRow(
            label = "부",
            value = listOf(father).toImmutableList(),
            goToPerson = goToPerson,
        )
        LinkRow(
            label = "모",
            value = listOf(mother).toImmutableList(),
            goToPerson = goToPerson,
        )
        LinkRow(
            label = "배우자",
            value = listOf(spouse).toImmutableList(),
            goToPerson = goToPerson,
        )
        LinkRow(
            label = "형제",
            value = siblings,
            goToPerson = goToPerson,
        )
        LinkRow(
            label = "자식",
            value = children,
            goToPerson = goToPerson,
        )
    }
}

@Composable
private fun LinkRow(
    label: String,
    value: ImmutableList<PersonEntity?>,
    goToPerson: (Int) -> Unit,
) {
    Row {
        Text("$label : ")
        value.forEach { person ->
            if (person == null) return
            Text(
                text = person.name,
                modifier = Modifier.clickable { goToPerson(person.key) },
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline,
                    ),
            )
        }
    }
}
