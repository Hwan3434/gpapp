package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GpWindow(
    mapViewModel: MapViewModel,
    tempTomb: TempTomb
) {
    val p = tempTomb.person.first()
    val caption = if(p.gender) {
        p.family
    } else {
        p.name
    }
    val keysArray = arrayOf(tempTomb.isWindowVisible)

    MarkerComposable(
        keys = keysArray,
        state = MarkerState(LatLng(tempTomb.tomb.location.latitude + 0.0001, tempTomb.tomb.location.longitude)),
        captionText = caption,
        onClick = {
            mapViewModel.toggleWindowVisible(tempTomb.tomb.key)
            false
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(tempTomb.isWindowVisible){
                Text(p.family,
                    fontSize = 20.sp,
                    color = Color.Magenta
                )
            }
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Person",
                tint = Color.Magenta,
            )
        }
    }
}