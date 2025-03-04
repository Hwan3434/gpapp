package jeonghwan.app.modules.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_NAME = "app_database"
const val DATABASE_PERSON_FAVORITE_TABLE = "person_favorite_table"
@Database(entities = [PersonData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personFavoriteDatasource(): PersonFavoriteDatasource
}
