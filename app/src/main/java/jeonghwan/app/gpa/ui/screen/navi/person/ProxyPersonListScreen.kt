package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProxyPersonListScreen(
    viewModel: PersonListViewModel = hiltViewModel(),
    goToPersonDetail: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteSet by viewModel.favoriteFlow.collectAsState(initial = emptySet())

    val listState = rememberLazyListState()

    // 바닥에 닿았을 때 이벤트 처리
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == null) {
                    // 최초호출
                    viewModel.loadPaging()
                    return@collect
                }

                if (lastVisibleItemIndex == uiState.personData.size - 1) {
                    run {
                        viewModel.loadPaging()
                    }
                }
            }
    }

    TestPersonListScreen(
        uiState = { uiState },
        listState = listState,
        isFavorite = {
            favoriteSet.contains(it)
        },
        onToggleFavorite = viewModel::toggleFavorite,
        onDetailScreen = goToPersonDetail,
    )
}
