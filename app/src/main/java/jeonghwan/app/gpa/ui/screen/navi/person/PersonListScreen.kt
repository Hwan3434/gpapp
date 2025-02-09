package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Column
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
        // 버튼 추가
        Button(onClick = { viewModel.loadPersonData() }) {
            Text("Load Person Data")
        }

        // 로딩 중일 때 표시
        if (uiState.isLoading) {
            Text("Loading...")
        }

        // 오류 메시지 표시
        uiState.errorMessage?.let { errorMessage ->
            Text("Error: $errorMessage")
        }

        // 데이터 표시
        Text("Person count: ${uiState.personData.size}")
    }
}