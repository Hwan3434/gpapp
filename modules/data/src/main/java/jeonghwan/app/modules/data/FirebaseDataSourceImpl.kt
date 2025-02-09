package jeonghwan.app.modules.data

import com.google.firebase.firestore.FirebaseFirestore
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel
import kotlinx.coroutines.tasks.await

class FirebaseDataSourceImpl (
    private val firebaseInstance: FirebaseFirestore
) : DataSourceInterface {

    override suspend fun getPersonAll(): List<PersonModel> {
        val temp = mutableListOf<PersonModel>()
        try {
            val res = firebaseInstance.collection("Person").get().await()
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
            val res = firebaseInstance.collection("Tomb").get().await()
            for (doc in res) {
                val tomb: TombModel = doc.toObject(TombModel::class.java)
                temp.add(tomb)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return temp
    }

    override suspend fun getPerson(key: Int): PersonModel {
        TODO("Not yet implemented")
    }

    override suspend fun getTomb(key: Int): TombModel {
        TODO("Not yet implemented")
    }
}