package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jeonghwan.app.domain.model.PersonEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PersonDetailWidget(
    person: PersonEntity,
    father: PersonEntity?,
    mother: PersonEntity?,
    spouse: PersonEntity?,
    children: List<PersonEntity>,
    goToPerson: (Int) -> Unit,
) {
    Column {
        Text("상세정보 :: ${person.name}")
        Text(modifier = Modifier.clickable {
            goToPerson(person.key)
        },text = "부 : ${father?.name}")
//        LinkRow("모 : ", listOf(mother).toImmutableList(), goToPerson)
//        LinkRow("배우자 : ", listOf(spouse).toImmutableList(), goToPerson)
//        LinkRow("자식 : ", children.toImmutableList(), goToPerson)
    }
}

//@Composable
//private fun LinkRow(
//    label: String,
//    value: ImmutableList<PersonEntity?>,
//    goToPerson: (Int) -> Unit,
//) {
//    Row {
//        Text(label)
//        value.forEach { person ->
//            Text(
//                text = person?.name ?: "none",
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//        }
//    }
//}
