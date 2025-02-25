package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PersonUiState(
    val personData: List<PersonEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLastPage: Boolean = false,
)

@HiltViewModel
class PersonListViewModel
    @Inject
    constructor(
        private val personUseCase: PersonUseCaseInterface,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PersonUiState())
        val uiState: StateFlow<PersonUiState> = _uiState

        private var lastVisibleDocument: DocumentSnapshot? = null

        fun loadPaging() {
            if (_uiState.value.isLoading || _uiState.value.isLastPage) {
                return
            }

            _uiState.value = _uiState.value.copy(isLoading = true)

            viewModelScope.launch {
                val result = personUseCase.getPersonPaging(lastVisibleDocument)
                _uiState.value =
                    when {
                        result.isSuccess -> {
                            lastVisibleDocument =
                                result.getOrNull()?.let { (list, snapshot) ->
                                    list.takeIf { it.size == 20 }?.let { snapshot }
                                }

                            _uiState.value.copy(
                                personData = _uiState.value.personData + (result.getOrNull()?.first ?: emptyList()),
                                isLoading = false,
                                errorMessage = null,
                                isLastPage = lastVisibleDocument == null,
                            )
                        }
                        result.isFailure -> {
                            _uiState.value.copy(
                                isLoading = false,
                                errorMessage = result.exceptionOrNull()?.message,
                            )
                        }
                        else -> {
                            _uiState.value.copy(
                                isLoading = false,
                                errorMessage = "Unknown error",
                            )
                        }
                    }
            }
        }
    }
