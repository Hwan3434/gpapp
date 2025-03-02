package jeonghwan.app.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PersonEntity(
    val key: Int,
    val spouse: Int?,
    val generator: Int?,
    val genderType: GenderType,
    val alive: Boolean,
    val clan: String?,
    val etc: String?,
    val family: String,
    val name: String,
    val tombKey: Int?,
    val dateDeath: Long?,
    val father: Int?,
    val mather: Int?,
) {
    override fun equals(other: Any?): Boolean {
        if(other !is PersonEntity) return false
        return key == other.key
    }
    override fun hashCode(): Int {
        return key.hashCode()
    }
}

// 성별 타입
enum class GenderType {
    Male,
    Female
}

fun PersonEntity.getFamilyName(): String {
    return if(genderType == GenderType.Female) {
        family
    }else {
        "$family $clan"
    }
}
