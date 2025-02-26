package jeonghwan.app.modules.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "app_database"
const val DATABASE_PERSON_FAVORITE_TABLE = "person_favorite_table"

@Database(entities = [PersonData::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personFavoriteDatasource(): PersonFavoriteDatasource
}
//
//class Converters {
//    @TypeConverter
//    fun fromLocalDateTime(dateTime: LocalDateTime?): Long? {
//        return dateTime?.toFLong()
//    }
//
//    @TypeConverter
//    fun toLocalDateTime(dateTimeLong: Long?): LocalDateTime? {
//        return dateTimeLong?.toFLocalDateTime()
//    }
//}

