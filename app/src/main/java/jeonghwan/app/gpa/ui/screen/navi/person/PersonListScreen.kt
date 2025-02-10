package jeonghwan.app.gpa.ui.screen.navi.person

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PersonListScreen(viewModel: PersonListViewModel = hiltViewModel(), onNextButtonClicked: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // 바닥에 닿았을 때 이벤트 처리
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if(uiState.isLastPage || uiState.isLoading) {
                    return@collect
                }
                if (lastVisibleItemIndex == uiState.personData.lastIndex) {
                    viewModel.getLoadPaging()
                }
            }
    }

    Column {
        uiState.errorMessage?.let { errorMessage ->
            Text("Error: $errorMessage")
        }

        LazyColumn(state = listState) {
            items(uiState.personData) { person ->
                PersonItem(person, onNextButtonClicked)
                HorizontalDivider(thickness = 1.dp, color = Color.Gray) // 경계선 추가
            }

            if (!uiState.isLastPage) {
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center // 수평 및 수직 중앙 정렬
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

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