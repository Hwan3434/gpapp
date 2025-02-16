package jeonghwan.app.entity

data class PersonEntity(
    val key: Int,
    val spouse: Int?,
    val generator: Int?,
    val gender: Boolean,
    val alive: Boolean,
    val clan: String?,
    val etc: String?,
    val family: String,
    val name: String,
    val tombKey: Int?,
    val dateDeath: Long?,
    val father: Int?,
    val mather: Int?,
)

fun PersonEntity.getFamilyName(): String {
    return "$family $clan"
}
