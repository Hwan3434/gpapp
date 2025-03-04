package jeonghwan.app.gpa.ui.screen.navi.detail.person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    personKey: Int,
    goToMap: (Int) -> Unit,
    goToPerson: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            Text(
                text = "Person Detail Screen: $personKey",
            )
            Text(
                text = "name: $personKey",
            )
            Button(
                onClick = {
                    goToMap(personKey)
                },
            ) {
                Text("지도에서보기")
            }
        }
    }
}
