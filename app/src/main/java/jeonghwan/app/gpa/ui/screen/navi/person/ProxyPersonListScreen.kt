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
    onFavoriteButtonClicked: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    PersonListScreen(
        modifier = modifier,
        uiState = uiState,
        onLoadPage = { viewModel.loadPaging() },
        onDetailButtonClicked = onDetailButtonClicked,
        onFavoriteButtonClicked = onFavoriteButtonClicked,
    )
}
