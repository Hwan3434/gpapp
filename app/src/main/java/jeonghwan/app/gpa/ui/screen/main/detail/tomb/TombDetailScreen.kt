package jeonghwan.app.gpa.ui.screen.main.detail.tomb

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TombDetailScreen(
    modifier: Modifier = Modifier,
    tombKey: Int,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Text(
            modifier = modifier.padding(innerPadding),
            text = "Tomb Detail Screen: $tombKey",
        )
    }
}
