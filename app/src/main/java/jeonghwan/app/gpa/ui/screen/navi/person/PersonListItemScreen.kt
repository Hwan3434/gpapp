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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.entity.getFamilyName
import jeonghwan.app.gpa.R

@Composable
fun PersonItem(
    modifier: Modifier = Modifier,
    person: PersonEntity,
    onDetailButtonClicked: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = { onDetailButtonClicked(person.personKey) }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // 세로 가운데 정렬
        ) {

            // 생사 유무 아이콘
            Icon(
                imageVector = if (person.alive) Icons.Default.Check else Icons.Default.Close,
                contentDescription = if (person.alive) "Alive" else "Deceased",
                tint = if (person.alive) Color.Green else Color.Red,
                modifier = modifier
                    .wrapContentSize()
                    .padding(end = 16.dp)
            )

            Column(
                modifier = modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                val fullFamilyName = if(person.gender) {
                    person.family
                }else {
                    person.getFamilyName()
                }
                val generator = if(person.gender) {
                    ""
                }else {
                    "${person.generator}${stringResource(R.string.generator_suffix)}"
                }
                Text("${person.name} ${if (person.gender) "女" else "男"}")
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Text("$fullFamilyName $generator")
            }
            IconButton(
                onClick = { onFavoriteButtonClicked(person.personKey) },
            ) {
                Icon(Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
            }
        }
    }
}

@Preview
@Composable
fun PersonItemPreview() {
    PersonItem(
        person = PersonEntity(
            personKey = 1,
            name = "이정환",
            family = "가평이씨",
            clan = "사직공파",
            alive = true,
            etc = "",
            spouse = 0,
            generator = 1,
            gender = true,
            tombKey = 1,
        ),
        onDetailButtonClicked = {},
        onFavoriteButtonClicked = {},
    )
}