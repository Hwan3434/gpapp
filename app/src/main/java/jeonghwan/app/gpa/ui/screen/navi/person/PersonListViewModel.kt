package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.domain.entity.PersonEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val personUseCase: PersonUseCaseInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonUiState())
    val uiState: StateFlow<PersonUiState> = _uiState

    fun loadPersonData() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = personUseCase.getPersonAll()
            _uiState.value = when {
                result.isSuccess -> {
                    _uiState.value.copy(
                        personData = result.getOrNull() ?: emptyList(),
                        isLoading = false,
                        errorMessage = null
                    )
                }

                result.isFailure -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }

                else -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Unknown error"
                    )
                }
            }
        }
    }
}

data class PersonUiState(
    val personData: List<PersonEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)