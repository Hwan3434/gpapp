package jeonghwan.app.gpa.ui.screen.main.detail.person

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class PersonDetailUiState(
    val isLoading: Boolean = false,
    val personEntity: PersonEntity? = null,
    val isFavorite: Boolean = false,
    val father: PersonEntity? = null,
    val mother: PersonEntity? = null,
    val spouse: PersonEntity? = null,
    val children: List<PersonEntity> = emptyList(),
)

@HiltViewModel
class PersonDetailViewModel
    @Inject
    constructor(
        private val personUseCaseInterface: PersonUseCaseInterface,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PersonDetailUiState())
        val uiState: StateFlow<PersonDetailUiState> = _uiState

        fun fetchPersonDetail(personKey: Int) {
            _uiState.value = _uiState.value.copy(isLoading = true)
            viewModelScope.launch {
                delay(1000L)
                val result = personUseCaseInterface.getPerson(personKey)
                val resultFavorite = personUseCaseInterface.isFavorite(personKey)
                if (result.isSuccess) {
                    val entity: PersonEntity? = result.getOrNull()
                    if (entity != null) {
                        var father: PersonEntity? = null
                        if (entity.father != null) {
                            father = personUseCaseInterface.getPerson(entity.father!!).getOrNull()
                        }
                        var mother: PersonEntity? = null
                        if (entity.mather != null) {
                            mother = personUseCaseInterface.getPerson(entity.mather!!).getOrNull()
                        }
                        var spouse: PersonEntity? = null
                        if (entity.spouse != null) {
                            spouse = personUseCaseInterface.getPerson(entity.spouse!!).getOrNull()
                        }
                        val children: List<PersonEntity>? = personUseCaseInterface.getPersonChild(entity.key).getOrNull()
                        _uiState.value =
                            _uiState.value.copy(
                                personEntity = result.getOrNull(),
                                father = father,
                                mother = mother,
                                spouse = spouse,
                                isFavorite = resultFavorite,
                                children = children ?: emptyList(),
                                isLoading = false,
                            )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }

        fun toggleFavorite(personKey: Int) {
            viewModelScope.launch {
                val person = personUseCaseInterface.getPerson(personKey).getOrNull()
                if (person != null) {
                    if (personUseCaseInterface.isFavorite(personKey)) {
                        personUseCaseInterface.delete(personKey)
                        _uiState.value = _uiState.value.copy(isFavorite = false)
                    } else {
                        personUseCaseInterface.insert(person)
                        _uiState.value = _uiState.value.copy(isFavorite = true)
                    }
                }
            }
        }
    }
