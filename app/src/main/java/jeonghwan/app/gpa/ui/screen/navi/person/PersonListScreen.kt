package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PersonListScreen(viewModel: PersonListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Button(onClick = { viewModel.getLoadPaging() }) {
            Text("Load Person Data")
        }

        if (uiState.isLoading) {
            Text("Loading...")
        }

        uiState.errorMessage?.let { errorMessage ->
            Text("Error: $errorMessage")
        }

        Text("Person count: ${uiState.personData.size}")

        if (uiState.showDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissDialog() },
                confirmButton = {
                    Button(onClick = { viewModel.dismissDialog() }) {
                        Text("확인")
                    }
                },
                title = { Text("데이터 로드 성공") },
                text = { Text("데이터를 성공적으로 가져왔습니다.") }
            )
        }
    }
}