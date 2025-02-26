package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProxyPersonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonListViewModel = hiltViewModel(),
    onDetailButtonClicked: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteSet by viewModel.favoriteFlow.collectAsState(initial = emptySet())

    if (uiState.personData.isEmpty() && !uiState.isLoading && !uiState.isLastPage) {
        viewModel.loadPaging()
    }

    PersonListScreen(
        modifier = modifier,
        uiState = uiState,
        favorite = favoriteSet::contains,
        onLoadPage = viewModel::loadPaging,
        onDetailButtonClicked = onDetailButtonClicked,
        onFavoriteButtonClicked = viewModel::toggleFavorite,
    )
}
