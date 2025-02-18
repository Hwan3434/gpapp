package jeonghwan.app.gpa.ui.screen.navi.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PersonDetailScreen(
    modifier: Modifier = Modifier,
    personKey: Int,
    onFavoriteButtonClicked: (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Text(
            modifier = modifier.padding(innerPadding),
            text = "Person Detail Screen: $personKey",
        )
    }
}
