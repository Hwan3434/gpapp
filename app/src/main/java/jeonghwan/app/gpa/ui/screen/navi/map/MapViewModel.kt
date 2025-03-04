package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.domain.model.GenderType
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.domain.model.TombEntity
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TombDataModel(
    val tomb: TombEntity,
    val persons: List<PersonEntity>,
    val isWindowVisible: Boolean = false,
) {
    // 묘에 묻힌 사람이 없는 경우는 묘를 표시 하지 않 습니다.(Data 를 가져올 때 필터링 에서 제외)
    private fun isFirstPerson(): Boolean {
        return persons.isNotEmpty()
    }

    fun getFirstPerson(): PersonEntity {
        assert(isFirstPerson())
        return persons.first()
    }

    fun getFirstPersonKey(): Int {
        assert(isFirstPerson())
        return persons.first().key
    }

    fun getCaption(): String {
        assert(isFirstPerson())
        val p = persons.first()
        return if (p.genderType == GenderType.Female) p.family else p.name
    }

    fun getColor(): Color {
        assert(isFirstPerson())
        return if (persons.first().genderType == GenderType.Female) Color.Magenta else Color.Blue
    }
}

@Immutable
data class MapUiState(
    val tombs: List<TombDataModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastBackPressedTime: Long = 0L,
) {
    companion object {
        const val BACK_TIME = 1000L
    }
}

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val personUseCase: PersonUseCaseInterface,
        private val tombUseCase: TombUseCaseInterface,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MapUiState())
        val uiState: StateFlow<MapUiState> = _uiState

        fun toggleTombPopupWindowVisibility(tombKey: Int) {
            _uiState.value =
                _uiState.value.copy(
                    tombs =
                        _uiState.value.tombs.map { tombData ->
                            tombData.copy(isWindowVisible = tombData.tomb.key == tombKey && !tombData.isWindowVisible)
                        },
                )
        }

        fun loadTombData() {
            if (!(_uiState.value.tombs.isEmpty() && !_uiState.value.isLoading && _uiState.value.errorMessage == null)) {
                return
            }
            _uiState.value = _uiState.value.copy(isLoading = true)
            viewModelScope.launch {
                val result = tombUseCase.getTombAll()
                val resultPerson = personUseCase.getPersonAll()
                result.onSuccess { tombs ->
                    _uiState.value =
                        _uiState.value.copy(
                            tombs =
                                tombs.mapNotNull { tomb ->
                                    val persons = resultPerson.getOrNull()?.filter { it.tombKey == tomb.key } ?: emptyList()
                                    if (persons.isEmpty()) {
                                        null
                                    } else {
                                        TombDataModel(tomb = tomb, persons = persons)
                                    }
                                },
                            isLoading = false,
                            errorMessage = null,
                        )
                }.onFailure { exception ->
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message,
                        )
                }
            }
        }

        fun onBackEvent(): Boolean {
            if (System.currentTimeMillis() - _uiState.value.lastBackPressedTime <= MapUiState.BACK_TIME) {
                return true
            } else {
                _uiState.value = _uiState.value.copy(lastBackPressedTime = System.currentTimeMillis())
                return false
            }
        }
    }
