package jeonghwan.app.gpa.ui.screen.navi.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.domain.model.TombEntity
import jeonghwan.app.gpa.R
import jeonghwan.app.modules.di.common.toFormattedDate

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun GpWindow(
    tomb: TombDataModel,
    onClick: (TombEntity) -> Unit,
    onDetailClick: (Int) -> Unit,
) {
    val keysArray = arrayOf(tomb.isWindowVisible)

    MarkerComposable(
        keys = keysArray,
        state =
            MarkerState(
                LatLng(
                    tomb.tomb.location.latitude,
                    tomb.tomb.location.longitude,
                ),
            ),
        captionText = tomb.getCaption(),
        onClick = {
            if (tomb.isWindowVisible) {
                onDetailClick(tomb.getFirstPersonKey())
                false
            } else {
                onClick(tomb.tomb)
                true
            }
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (tomb.isWindowVisible) {
                GpWindowBox(tomb.getFirstPerson())
            }

            Spacer(modifier = Modifier.padding(2.dp))

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Person",
                tint = tomb.getColor(),
            )
        }
    }
}

@Composable
fun GpWindowBox(p: PersonEntity) {
    val dateDeath =
        if (p.dateDeath == null || p.dateDeath == 0L) stringResource(R.string.non_death_date) else p.dateDeath!!.toFormattedDate()
    val etc = if (p.etc == null || p.etc == "") stringResource(R.string.non_etc) else p.etc!!
    Box(
        modifier =
            Modifier
                .width(200.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("${p.generator}${stringResource(R.string.generator_suffix)}", fontSize = 20.sp)
            Text("${stringResource(R.string.death_date)} : $dateDeath", fontSize = 20.sp)
            Text(etc, fontSize = 20.sp)
        }
    }
}
