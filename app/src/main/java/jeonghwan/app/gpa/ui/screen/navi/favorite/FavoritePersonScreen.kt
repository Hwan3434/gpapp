package jeonghwan.app.gpa.ui.screen.navi.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.gpa.ui.common.LazyPagingGrid

@Composable
fun FavoritePersonScreen(
    viewModel: FavoritePersonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagedFavorites = viewModel.personFavorites.collectAsLazyPagingItems()

    FavoriteUiScreen(
        state = uiState,
        lazyPagingItems = pagedFavorites,
    )
}

@Composable
fun FavoriteUiScreen(
    state: FavoritePersonUiState,
    lazyPagingItems: LazyPagingItems<PersonEntity>,
) {
    LazyPagingGrid(
        lazyPagingItems = lazyPagingItems,
    ) {
        FavoriteItem(
            person = it,
        )
    }
}
