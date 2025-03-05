package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import jeonghwan.app.domain.model.PersonEntity
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PersonDetailWidget(
    person: PersonEntity?,
    father: PersonEntity?,
    goToPerson: (Int) -> Unit,
) {
    Column {
        Text("상세정보 :: ${person?.name}")
        Text("부 :: ${father?.name}")
        Button(
            onClick = {
                if (father != null) {
                    goToPerson(father.key)
                }
            },
        ) {
            Text("자세히 보기")
        }
    }
}

@Composable
private fun LinkRow(
    label: String,
    value: ImmutableList<PersonEntity?>,
    goToPerson: (Int) -> Unit,
) {
    Row {
        Text("$value")
//        value.forEach { person ->
//            Text(
//                text = person?.name ?: "null",
// //                modifier = Modifier.clickable {
// //                    if (person != null) {
// //                        goToPerson(person.key)
// //                    }
// //                },
// //                style =
// //                TextStyle().copy(
// //                    color = Color.Blue,
// //                    textDecoration = TextDecoration.Underline,
// //                ),
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//        }
    }
}
