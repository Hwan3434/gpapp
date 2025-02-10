package jeonghwan.app.modules.data.model

data class PersonModel(
    val personKey: Int = -1, // 식별키
    val spouse: Int? = null, // 배우자 식별키
    val alive: Boolean = false, // 생사여부
    val etc: String = "", // 설명
    val clan: String = "", // 본관
    val family: String = "", // 성씨
    val name : String = "", // 이름
    val gender : Boolean = true, // 성별
    val generator: Int? = null, // 몇대손
    val tombKey: Int? = null, // 묘 식별키
    val father: Int? = null, // 아버지 식별키
    val mather: Int? = null, // 어머니 식별키
    val birth: String = "", // 출생일
    val dateDeath: Long? = 0, // 사망일
)
