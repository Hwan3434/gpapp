package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PersonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonListViewModel = hiltViewModel(),
    onDetailButtonClicked: (Int) -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // 바닥에 닿았을 때 이벤트 처리
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (uiState.isLastPage || uiState.isLoading) {
                    return@collect
                }
                if (lastVisibleItemIndex == uiState.personData.lastIndex) {
                    viewModel.loadPaging()
                }
            }
    }

    Column {
        uiState.errorMessage?.let { errorMessage ->
            Text("Error: $errorMessage")
        }

        LazyColumn(
            modifier = modifier.padding(16.dp),
            state = listState
        ) {
            items(uiState.personData) { person ->
                PersonItem(
                    person = person,
                    onDetailButtonClicked = onDetailButtonClicked,
                    onFavoriteButtonClicked = onFavoriteButtonClicked
                )
            }

            if (!uiState.isLastPage) {
                item {
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
    }
}