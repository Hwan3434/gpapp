package jeonghwan.app.modules.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DATABASE_PERSON_FAVORITE_TABLE)
data class PersonData(
    @PrimaryKey val key: Int,
    val name: String,
    val alive: Boolean,
    val genderType: Boolean,
    val family: String,
)
