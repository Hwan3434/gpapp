package jeonghwan.app.modules.domain.entity

data class PersonEntity(
    val personKey: Int,
    val spouse: Int?,
    val alive: Boolean,
    val clan: String?,
    val etc: String?,
    val family: String,
    val name: String
)
