package jeonghwan.app.modules.data

import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel

interface DataSourceInterface {
    suspend fun getPersonPaging(documentSnapshot: DocumentSnapshot?, size: Int?): Pair<List<PersonModel>, DocumentSnapshot?>
    suspend fun getPersonAll(): List<PersonModel>
    suspend fun getTombAll(): List<TombModel>
    suspend fun getPerson(key: Int): PersonModel?
    suspend fun getPersonChild(key: Int): List<PersonModel>
    suspend fun getTomb(key: Int): TombModel?
}