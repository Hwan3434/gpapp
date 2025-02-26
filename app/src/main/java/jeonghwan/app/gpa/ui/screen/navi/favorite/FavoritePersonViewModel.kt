package jeonghwan.app.gpa.ui.screen.navi.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class FavoritePersonUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLastPage: Boolean = false,
)

@HiltViewModel
class FavoritePersonViewModel
    @Inject
    constructor(
        personUseCase: PersonUseCaseInterface,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FavoritePersonUiState())
        val uiState: StateFlow<FavoritePersonUiState> = _uiState

        val personFavorites = personUseCase.getPagedFavorites().cachedIn(viewModelScope)
    }
