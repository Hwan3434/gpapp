package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import jeonghwan.app.entity.TombEntity


fun MapType.changedNextMapType(): MapType {
    return when (this) {
        MapType.Basic -> MapType.Hybrid
        MapType.Hybrid -> MapType.Navi
        MapType.Navi -> MapType.Terrain
        MapType.Terrain -> MapType.Satellite
        MapType.Satellite -> MapType.NaviHybrid
        MapType.NaviHybrid -> MapType.Basic
        MapType.None -> MapType.Basic
    }
}

fun MapType.toKorean(): String {
    return when (this) {
        MapType.Basic -> "일반"
        MapType.Hybrid -> "위성(겹쳐보기)"
        MapType.Navi -> "네비게이션"
        MapType.Terrain -> "지형도"
        MapType.Satellite -> "위성"
        MapType.NaviHybrid -> "네비게이션 위성"
        MapType.None -> "없음"
    }
}

private val naviMapSaver = Saver<MapType, String>(
    save = { it.value.name },
    restore = { route ->
        route.let {
            when (it) {
                MapType.Basic.value.name -> MapType.Basic
                MapType.Hybrid.value.name -> MapType.Hybrid
                MapType.Navi.value.name -> MapType.Navi
                MapType.Terrain.value.name -> MapType.Terrain
                MapType.Satellite.value.name -> MapType.Satellite
                MapType.NaviHybrid.value.name -> MapType.NaviHybrid
                MapType.None.value.name -> MapType.None
                else -> MapType.Satellite
            }
        }
    }
)

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    MapItemScreen(
        modifier = modifier,
        uiState = uiState,
    ) { tomb ->
        viewModel.toggleWindowVisible(tomb.key)
        false
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapItemScreen(
    modifier: Modifier,
    uiState: MapUiState,
    onClick: (TombEntity) -> Boolean = { false }
) {

    var mapState by rememberSaveable(stateSaver = naviMapSaver) { mutableStateOf(MapType.Satellite) }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center // 수평 및 수직 중앙 정렬
        ) {
            CircularProgressIndicator()
        }
    }

    val cameraPositionState = rememberCameraPositionState(
        init = {
            position = CameraPosition(LatLng(36.615743, 128.352462), 16.0)
        }
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NaverMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow,
                mapType = mapState,
            ),
            uiSettings = MapUiSettings(
                isCompassEnabled = true,
                isRotateGesturesEnabled = false,
                isLogoClickEnabled = false,
            )
        ) {
            uiState.tempTomb.map { tempTomb ->
                GpWindow(
                    tempTomb = tempTomb,
                    onClick = { _ ->
                        onClick(tempTomb.tomb)
                    }
                )
            }
        }

        MapFloating(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            text = mapState.toKorean(),
        ) {
            mapState = mapState.changedNextMapType()
        }
    }
}
