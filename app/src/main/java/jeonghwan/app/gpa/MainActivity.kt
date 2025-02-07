package jeonghwan.app.gpa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jeonghwan.app.gpa.ui.theme.GpaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GpaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tab 1", "Tab 2", "Tab 3")

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> TabContent1()
            1 -> TabContent2()
            2 -> TabContent3()
        }
    }
}

@Composable
fun TabContent1() {
    Text("Content for Tab 1")
}

@Composable
fun TabContent2() {
    Text("Content for Tab 2")
}

@Composable
fun TabContent3() {
    Text("Content for Tab 3")
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    GpaTheme {
        MainScreen()
    }
}
