package jeonghwan.app.modules.di.usecase

import androidx.paging.PagingData
import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.domain.model.PersonEntity
import kotlinx.coroutines.flow.Flow

interface PersonUseCaseInterface {
    suspend fun insert(person: PersonEntity)
    fun getPagedFavorites(): Flow<PagingData<PersonEntity>>
    fun getFavorites(): Flow<List<PersonEntity>>
    suspend fun isFavorite(personKey: Int): Boolean
    suspend fun delete(personKey: Int)
    suspend fun getPersonPaging(page: DocumentSnapshot?, size: Int = 20): Result<Pair<List<PersonEntity>, DocumentSnapshot?>>
    suspend fun getPersonAll(): Result<List<PersonEntity>>
    suspend fun getPerson(key: Int): Result<PersonEntity?>
}