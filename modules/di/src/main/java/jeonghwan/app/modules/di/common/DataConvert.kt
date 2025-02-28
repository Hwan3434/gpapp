package jeonghwan.app.modules.di.common

import jeonghwan.app.domain.model.GenderType
import jeonghwan.app.domain.model.GpGeoPoint
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel
import jeonghwan.app.domain.model.TombEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun PersonModel.toEntity() = PersonEntity(
    key = personKey,
    spouse = spouse,
    alive = alive,
    etc = etc,
    clan = clan,
    family = family,
    name = name,
    generator = generator,
    genderType = if(gender) GenderType.Female else GenderType.Male,
    tombKey = tombKey,
    dateDeath = dateDeath,
    father = father,
    mather = mather
)

fun TombModel.toEntity(): TombEntity? {
    if (tombKey == null || latitude == null || longitude == null) return null
    return TombEntity(
        key = tombKey!!,
        name = name,
        location = GpGeoPoint(latitude!!, longitude!!)
    )
}


fun Long.toFormattedDate(): String {
    // Long을 Date로 변환
    val date = Date(this)
    // 날짜 포맷터 정의
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    // 포맷팅된 날짜 문자열 반환
    return formatter.format(date)
}
