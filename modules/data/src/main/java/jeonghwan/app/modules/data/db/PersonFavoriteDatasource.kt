package jeonghwan.app.modules.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonFavoriteDatasource {
    @Insert
    suspend fun insert(person: PersonData)

    @Query("SELECT * FROM $DATABASE_PERSON_FAVORITE_TABLE")
    fun getPagedFavorites(): PagingSource<Int, PersonData>

    @Query("SELECT * FROM $DATABASE_PERSON_FAVORITE_TABLE")
    fun getFavorites(): Flow<List<PersonData>>

    @Query("SELECT COUNT(*) FROM $DATABASE_PERSON_FAVORITE_TABLE WHERE \"key\" = :personKey")
    suspend fun isPersonKeyExists(personKey: Int): Int

    @Query("DELETE FROM $DATABASE_PERSON_FAVORITE_TABLE WHERE \"key\" = :personKey")
    suspend fun delete(personKey: Int)
}