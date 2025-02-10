package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jeonghwan.app.entity.PersonEntity

@Composable
fun PersonItem(person: PersonEntity, onNextButtonClicked: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text("Name: ${person.name}")
        Text("Family: ${person.family}")
        Button(
            onClick = { onNextButtonClicked(person.personKey) }
        ) {
            Text("Next")
        }
    }
}
