package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.compose.MapType
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.entity.TombEntity
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class MapUiState(
    val tempTomb: List<TempTomb> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class MapViewModel @Inject constructor(
    private val personUseCase: PersonUseCaseInterface,
    private val tombUseCase: TombUseCaseInterface
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState

    init {
        loadTomb()
    }

    fun toggleWindowVisible(tombKey: Int) {
        _uiState.value = _uiState.value.copy(
            tempTomb = _uiState.value.tempTomb.map {
                if (it.tomb.key == tombKey) {
                    it.copy(isWindowVisible = !it.isWindowVisible)
                } else {
                    it.copy(isWindowVisible = false)
                }
            }
        )
    }

    private fun loadTomb() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = tombUseCase.getTombAll()
            val resultPerson = personUseCase.getPersonAll()
            Timber.d("result: ${result.isSuccess} || ${result.getOrNull()?.count()}")
            _uiState.value = when {
                result.isSuccess -> {

                    val tempTomb = result.getOrNull()?.mapNotNull { tomb ->
                        val persons = resultPerson.getOrNull()?.filter { it.tombKey == tomb.key } ?: emptyList()
                        if (persons.isEmpty()) null
                        else TempTomb(
                            tomb = tomb,
                            person = persons
                        )
                    } ?: emptyList()

                    _uiState.value.copy(
                        tempTomb = tempTomb,
                        isLoading = false,
                        errorMessage = null
                    )
                }
                else -> {
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }

    }
}

data class TempTomb(
    val isWindowVisible: Boolean = false,
    val tomb: TombEntity,
    val person: List<PersonEntity>
)