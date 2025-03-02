package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jeonghwan.app.domain.model.GenderType
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.domain.model.getFamilyName
import jeonghwan.app.gpa.R

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    person: PersonEntity,
    isFavorite: (PersonEntity) -> Boolean,
    onToggleFavorite: (PersonEntity) -> Unit,
    onDetailButtonClicked: (Int) -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onDetailButtonClicked(person.key) },
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 생사 유무 아이콘
            Icon(
                imageVector = if (person.alive) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (person.alive) "Alive" else "Deceased",
                tint = if (person.alive) Color.Green else Color.Red,
                modifier =
                    modifier
                        .wrapContentSize()
                        .padding(end = 16.dp),
            )

            Column(
                modifier = modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                val fullFamilyName = person.getFamilyName()
                val generator = person.getGenerator()
                Text("${person.name} ${if (person.genderType == GenderType.Female) "女" else "男"}")
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Text("$fullFamilyName $generator")
            }
            IconButton(
                onClick = { onToggleFavorite(person) },
            ) {
                Icon(
                    if (isFavorite(person)) {
                        Icons.Default.Star
                    } else {
                        Icons.Default.MailOutline
                    },
                    contentDescription = "Star",
                )
            }
        }
    }
}

@Preview
@Composable
fun MaleItemPreview() {
    PersonItem(
        person =
            PersonEntity(
                key = 1,
                name = "철수",
                family = "가평이씨",
                clan = "사직공파",
                alive = false,
                etc = "",
                spouse = 0,
                generator = 1,
                genderType = GenderType.Male,
                tombKey = 1,
                dateDeath = 0,
                father = 0,
                mather = 0,
            ),
        onDetailButtonClicked = {},
        isFavorite = { false },
        onToggleFavorite = {},
    )
}

@Preview
@Composable
fun FemalePreview() {
    PersonItem(
        person =
            PersonEntity(
                key = 1,
                name = "영희",
                family = "가평이씨",
                clan = "사직공파",
                alive = true,
                etc = "",
                spouse = 0,
                generator = 1,
                genderType = GenderType.Female,
                tombKey = 1,
                dateDeath = 0,
                father = 0,
                mather = 0,
            ),
        onDetailButtonClicked = {},
        isFavorite = { false },
        onToggleFavorite = {},
    )
}

@Composable
private fun PersonEntity.getGenerator(): String {
    return if (genderType == GenderType.Female) {
        ""
    } else {
        "$generator${stringResource(R.string.generator_suffix)}"
    }
}
