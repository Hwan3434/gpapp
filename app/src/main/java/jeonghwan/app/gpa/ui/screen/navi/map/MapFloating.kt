package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MapFloating(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {

        Box(
            modifier = modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.padding(2.dp))

        FloatingActionButton(
            modifier = Modifier.padding(bottom = 4.dp),
            onClick = onClick,
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}


@Preview()
@Composable
fun MapFloatingPreview() {
    MapFloating(
        modifier = Modifier.fillMaxSize(),
        text = "인공위성",
        onClick = { }
    )
}


