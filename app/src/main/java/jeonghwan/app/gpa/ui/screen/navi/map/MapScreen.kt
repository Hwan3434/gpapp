package jeonghwan.app.gpa.ui.screen.navi.map

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import jeonghwan.app.gpa.R
import timber.log.Timber

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

@Composable
fun MapType.toKorean(): String {
    return when (this) {
        MapType.Basic -> stringResource(id = R.string.map_basic)
        MapType.Hybrid -> stringResource(id = R.string.map_hybrid)
        MapType.Navi -> stringResource(id = R.string.map_navi)
        MapType.Terrain -> stringResource(id = R.string.map_terrain)
        MapType.Satellite -> stringResource(id = R.string.map_satellite)
        MapType.NaviHybrid -> stringResource(id = R.string.map_navi_hybrid)
        MapType.None -> stringResource(id = R.string.map_none)
    }
}

private val naviMapSaver =
    Saver<MapType, String>(
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
        },
    )

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
    onNextButtonClicked: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.loadTombData()

    MapItemScreen(
        modifier = modifier,
        uiState = uiState,
        onClick = { tomb ->
            viewModel.toggleTombPopupWindowVisibility(tomb.key)
        },
        onDetailClick = onNextButtonClicked,
        onBackEvent = { viewModel.onBackEvent() },
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapItemScreen(
    modifier: Modifier,
    uiState: MapUiState,
    onClick: (TombEntity) -> Unit,
    onDetailClick: (Int) -> Unit,
    onBackEvent: () -> Boolean,
) {
    var mapState by rememberSaveable(stateSaver = naviMapSaver) { mutableStateOf(MapType.Satellite) }

    if (uiState.isLoading) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val cameraPositionState =
        rememberCameraPositionState(
            init = {
                position = CameraPosition(LatLng(36.615743, 128.352462), 14.0)
            },
        )

    val context = LocalContext.current
    val exitMessage = stringResource(R.string.toast_exit)
    BackOnPressed(
        uiState = uiState,
        onClick = onClick,
        onBackEvent = onBackEvent,
    ) {
        Toast.makeText(context, exitMessage, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        NaverMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties =
                MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                    mapType = mapState,
                ),
            uiSettings =
                MapUiSettings(
                    isCompassEnabled = true,
                    isRotateGesturesEnabled = false,
                    isLogoClickEnabled = false,
                ),
            onMapClick = { _, _ ->
                Timber.d("맵 클릭 !!")
                uiState.tombs.firstOrNull { it.isWindowVisible }?.let {
                    onClick(it.tomb)
                }
            },
        ) {
            uiState.tombs.map { tempTomb ->
                GpWindow(
                    tomb = tempTomb,
                    onClick = onClick,
                    onDetailClick = onDetailClick,
                )
            }
        }

        MapFloating(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
            text = mapState.toKorean(),
        ) {
            mapState = mapState.changedNextMapType()
        }
    }
}

@Composable
fun BackOnPressed(
    uiState: MapUiState,
    onBackEvent: () -> Boolean,
    onClick: (TombEntity) -> Unit,
    onExitMessage: () -> Unit,
) {
    val context = LocalContext.current

    BackHandler(enabled = true) {
        uiState.tombs.firstOrNull { it.isWindowVisible }?.let {
            onClick(it.tomb)
            return@BackHandler
        }

        if (onBackEvent()) {
            (context as Activity).finish()
        } else {
            onExitMessage()
        }
    }
}
