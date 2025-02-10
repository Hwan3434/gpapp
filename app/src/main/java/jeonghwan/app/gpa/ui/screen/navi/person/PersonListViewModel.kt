package jeonghwan.app.gpa.ui.screen.navi.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PersonUiState(
    val personData: List<PersonEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val isLastPage: Boolean = false // 마지막 페이지 여부
)

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val personUseCase: PersonUseCaseInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonUiState())
    val uiState: StateFlow<PersonUiState> = _uiState

    private var lastVisibleDocument: DocumentSnapshot? = null

    init {
        getLoadPaging()
    }

    fun getLoadPaging() {
        if (_uiState.value.isLastPage) {
            return
        } // 마지막 페이지면 로드하지 않음

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {

//            delay(2_000) // 1초 지연

            val result = personUseCase.getPersonPaging(lastVisibleDocument)
            _uiState.value = when {
                result.isSuccess -> {
                    lastVisibleDocument = result.getOrNull()?.let { (list, snapshot) ->
                        list.takeIf { it.size == 20 }?.let { snapshot }
                    }

                    _uiState.value.copy(
                        personData = _uiState.value.personData + (result.getOrNull()?.first ?: emptyList()),
                        isLoading = false,
                        errorMessage = null,
                        showDialog = lastVisibleDocument == null, // 마지막 페이지일 때만 다이얼로그 표시
                        isLastPage = lastVisibleDocument == null,
                    )
                }

                result.isFailure -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message,
                        showDialog = false
                    )
                }

                else -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Unknown error",
                        showDialog = false
                    )
                }
            }
        }
    }

    fun getReloadPaging() {
        lastVisibleDocument = null
        _uiState.value = _uiState.value.copy(isLastPage = false) // 상태 초기화
        getLoadPaging()
    }

    fun dismissDialog() {
        _uiState.value = _uiState.value.copy(showDialog = false)
    }
}