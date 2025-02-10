package jeonghwan.app.modules.di.usecase

import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.entity.PersonEntity

interface PersonUseCaseInterface {
    suspend fun getPersonPaging(page: DocumentSnapshot?, size: Int = 20): Result<Pair<List<PersonEntity>, DocumentSnapshot?>>
    suspend fun getPersonAll(): Result<List<PersonEntity>>
    suspend fun getPerson(key: Int): Result<PersonEntity?>
}