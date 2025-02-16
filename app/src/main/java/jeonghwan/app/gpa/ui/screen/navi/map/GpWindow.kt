package jeonghwan.app.gpa.ui.screen.navi.map

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import jeonghwan.app.entity.GpGeoPoint
import jeonghwan.app.entity.PersonEntity
import jeonghwan.app.entity.TombEntity
import jeonghwan.app.gpa.R
import jeonghwan.app.modules.di.common.toFormattedDate
import timber.log.Timber

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GpWindow(
    tempTomb: TempTomb,
    onClick: (TombEntity) -> Unit,
    onDetailClick: (Int) -> Unit,
) {
    val p = tempTomb.person.first()
    val caption = if (p.gender) {
        p.family
    } else {
        p.name
    }
    val color = if (p.gender) {
        Color.Magenta
    } else {
        Color.Blue
    }
    val keysArray = arrayOf(tempTomb.isWindowVisible)

    MarkerComposable(
        keys = keysArray,
        state = MarkerState(
            LatLng(
                tempTomb.tomb.location.latitude,
                tempTomb.tomb.location.longitude
            )
        ),
        captionText = caption,
        onClick = {
            if (tempTomb.isWindowVisible) {
                onDetailClick(p.key)
                false
            } else {
                onClick(tempTomb.tomb)
                true
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (tempTomb.isWindowVisible) {
                GpWindowBox(p)
            }

            Spacer(modifier = Modifier.padding(2.dp))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Person",
                tint = color,
            )

        }
    }
}

@Composable
fun GpWindowBox(
    p: PersonEntity,
) {
    val dateDeath =
        if (p.dateDeath == null || p.dateDeath == 0L) "알 수 없음" else p.dateDeath!!.toFormattedDate()
    val etc = if (p.etc == null || p.etc == "") "설명이 없습니다." else p.etc!!
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp)) // 라운딩된 배경
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp) // 줄 간격
        ) {
            Text("${p.generator}${stringResource(R.string.generator_suffix)}", fontSize = 20.sp)
            Text("${stringResource(R.string.death_date)} : $dateDeath", fontSize = 20.sp)
            Text(etc, fontSize = 20.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GpWindowPreview() {
    // 더미 데이터 생성
    val dummyPerson = PersonEntity(
        key = 1,
        name = "John Doe",
        family = "Doe Family",
        clan = "Doe Clan",
        alive = true,
        etc = "",
        spouse = 0,
        generator = 1,
        gender = true,
        tombKey = 1,
        dateDeath = 0,
        father = 0,
        mather = 0,
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
        onClick = {},
        onDetailClick = {}
    )
}
