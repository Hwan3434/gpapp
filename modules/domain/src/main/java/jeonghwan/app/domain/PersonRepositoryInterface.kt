package jeonghwan.app.domain

import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.entity.PersonEntity

interface PersonRepositoryInterface {
    suspend fun getPersonPaging(page: DocumentSnapshot?, size: Int): Pair<List<PersonEntity>, DocumentSnapshot?>
    suspend fun getPersonAll(): List<PersonEntity>
    suspend fun getPerson(key: Int): PersonEntity?
}