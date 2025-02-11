package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GpMarker(tempTomb: TempTomb) {
    Marker(
        state = MarkerState(LatLng(tempTomb.tomb.location.latitude, tempTomb.tomb.location.longitude)),
        captionText = tempTomb.person[0].name,
        captionOffset = 10.dp,
        captionColor = Color.Magenta,
        captionTextSize = 20.sp,
        captionRequestedWidth = 10.dp
    )
}