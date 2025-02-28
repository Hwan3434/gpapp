package jeonghwan.app.domain.model

data class TombEntity (
    val key: Int,
    val name: String,
    val location: GpGeoPoint
)

data class GpGeoPoint(
    val latitude: Double,
    val longitude: Double
)