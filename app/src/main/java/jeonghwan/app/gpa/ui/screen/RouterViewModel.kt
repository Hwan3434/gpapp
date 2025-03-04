package jeonghwan.app.gpa.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import jeonghwan.app.gpa.ui.screen.main.NaviItems
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RouterViewModel
    @Inject
    constructor(
        private val personUseCase: PersonUseCaseInterface,
        private val tombUseCase: TombUseCaseInterface,
    ) : ViewModel() {
        // 탭 상태를 관리합니다.
        var tab: NaviItems by mutableStateOf(NaviItems.Map)
            private set

        // 카메라 초기 위치를 관리합니다.
        var cameraPosition by mutableStateOf(
            CameraPosition(LatLng(36.614443, 128.35), 14.0),
        )
            private set

        // 탭 변경 함수(필요할 경우)
        fun updateTab(newTab: NaviItems) {
            tab = newTab
        }

        // 카메라 위치 변경 함수(필요할 경우)
        private fun updateCameraPosition(newPosition: CameraPosition) {
            cameraPosition = newPosition
        }

        fun updateCameraPositionByPersonKey(key: Int) {
            viewModelScope.launch {
                val person = personUseCase.getPerson(key)
                if (person.isSuccess && person.getOrNull() != null && person.getOrNull()?.tombKey != null) {
                    val tombKey = person.getOrNull()?.tombKey
                    val tomb = tombUseCase.getTomb(tombKey!!)
                    if (tomb.isSuccess && tomb.getOrNull() != null) {
                        updateCameraPosition(
                            CameraPosition(
                                LatLng(tomb.getOrNull()!!.location.latitude, tomb.getOrNull()!!.location.longitude),
                                17.0,
                            ),
                        )
                    } else {
                        Timber.d("Tomb is not exist")
                    }
                } else {
                    Timber.d("Person is not exist")
                }
            }
        }
    }
