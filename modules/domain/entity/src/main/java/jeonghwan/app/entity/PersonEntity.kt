package jeonghwan.app.entity

data class PersonEntity(
    val personKey: Int,
    val spouse: Int?,
    val generator: Int?,
    val gender: Boolean,
    val alive: Boolean,
    val clan: String?,
    val etc: String?,
    val family: String,
    val name: String,
    val tombKey: Int?,
)

fun PersonEntity.getFamilyName(): String {
    return "$family $clan"
}
