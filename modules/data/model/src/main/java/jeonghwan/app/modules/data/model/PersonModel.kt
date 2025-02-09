package jeonghwan.app.modules.data.model

data class PersonModel(
    val personKey: Int, // 식별키
    val spouse: Int?, // 배우자 식별키
    val alive: Boolean, // 생사여부
    val etc: String, // 설명
    val family: String, // 성씨
    val name : String, // 이름
    val gender : Boolean, // 성별
    val generator: Int, // 몇대손
    val tombKey: Int?, // 묘 식별키
)
