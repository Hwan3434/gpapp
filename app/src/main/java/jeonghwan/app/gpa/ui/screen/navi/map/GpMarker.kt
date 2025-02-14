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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.overlay.Marker
import jeonghwan.app.entity.GpGeoPoint
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.entity.TombEntity

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GpWindow(
    tempTomb: TempTomb,
    onClick: (Marker) -> Boolean = { false }
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
        onClick = onClick,
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


@Preview(showBackground = true)
@Composable
fun GpWindowPreview() {
    // 더미 데이터 생성
    val dummyPerson = PersonEntity(
        personKey = 1,
        name = "John Doe",
        family = "Doe Family",
        clan = "Doe Clan",
        alive = true,
        etc = "",
        spouse = 0,
        generator = 1,
        gender = true,
        tombKey = 1
    )

    val dummyTomb = TempTomb(
        tomb = TombEntity(
            key = 1,
            name = "Doe Tomb",
            location = GpGeoPoint(
                latitude = 37.5666103,
                longitude = 126.9783882
            )
        ),
        person = listOf(dummyPerson),
        isWindowVisible = true
    )

    // GpWindow 호출
    GpWindow(
        tempTomb = dummyTomb,
    ) {
        false
    }
}
