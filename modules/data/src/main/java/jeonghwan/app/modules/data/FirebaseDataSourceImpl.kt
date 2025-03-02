package jeonghwan.app.modules.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel
import kotlinx.coroutines.tasks.await

class FirebaseDataSourceImpl (
    private val firebaseInstance: FirebaseFirestore
) : DataSourceInterface {

    companion object {
        private const val PERSON_COLLECTION = "Person"
        private const val PERSON_PAGING_SIZE = 20L
        private const val PERSON_ORDER_BY_GENERATOR = "generator"
        private const val TOMB_COLLECTION = "Tomb"
    }

    /**
     * 지정된 크기(size)만큼의 PersonModel 목록을 페이징하여 반환합니다.
     *
     * @param documentSnapshot 이전 쿼리의 마지막 문서 스냅샷. 첫 번째 페이지인 경우 null을 전달합니다.
     * @param size 가져올 문서의 수. null인 경우 기본값 20을 사용합니다.
     * @return 페이징된 PersonModel 목록과 다음 페이지를 위한 마지막 문서 스냅샷.
     *
     * List의 count가 20개 미만이거나 DocumentSnapshot이 null이면 마지막 조회까지 완료한 것입니다.
     */
    override suspend fun getPersonPaging(
        documentSnapshot: DocumentSnapshot?, size: Int?
    ): Pair<List<PersonModel>, DocumentSnapshot?> {
        return try {
            val query = firebaseInstance.collection(PERSON_COLLECTION)
                .orderBy(PERSON_ORDER_BY_GENERATOR)
                .limit((size ?: PERSON_PAGING_SIZE).toLong())

            val pagedQuery = documentSnapshot?.let {
                query.startAfter(it)
            } ?: query

            val querySnapshot = pagedQuery.get().await()

            val persons = querySnapshot.documents.mapNotNull { it.toObject(PersonModel::class.java) }
            val newLastVisible = querySnapshot.documents.lastOrNull()

            Pair(persons, newLastVisible)
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(emptyList(), null)
        }
    }

    override suspend fun getPersonAll(): List<PersonModel> {
        val temp = mutableListOf<PersonModel>()
        try {
            val res = firebaseInstance.collection(PERSON_COLLECTION).get().await()
            for (doc in res) {
                val person: PersonModel = doc.toObject(PersonModel::class.java)
                temp.add(person)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return temp
    }

    override suspend fun getTombAll(): List<TombModel> {
        val temp = mutableListOf<TombModel>()

        try {
            val res = firebaseInstance.collection(TOMB_COLLECTION).get().await()
            for (doc in res) {
                val locationData = doc.get("location") as com.google.firebase.firestore.GeoPoint
                val tomb: TombModel = doc.toObject(TombModel::class.java)
                tomb.latitude = locationData.latitude
                tomb.longitude = locationData.longitude
                temp.add(tomb)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return temp
    }

    override suspend fun getPerson(key: Int): PersonModel? {
        return try {
            val querySnapshot = firebaseInstance.collection(PERSON_COLLECTION)
                .whereEqualTo("personKey", key)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject(PersonModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getTomb(key: Int): TombModel? {
        return try {
            val querySnapshot = firebaseInstance.collection(TOMB_COLLECTION)
                .whereEqualTo("tombKey", key)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                querySnapshot.documents[0].toObject(TombModel::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}