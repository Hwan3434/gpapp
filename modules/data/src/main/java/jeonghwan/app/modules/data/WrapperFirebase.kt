package jeonghwan.app.modules.data

import com.google.firebase.firestore.FirebaseFirestore
import jeonghwan.app.modules.data.models.PersonModel
import jeonghwan.app.modules.data.models.TombModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// Firebase Database Wrapper class
class WrapperFirebase {

    private val firebaseInstance = FirebaseFirestore.getInstance()

    fun getPerson(): Flow<List<PersonModel>> {
        return callbackFlow {
            val temp = mutableListOf<PersonModel>()
            firebaseInstance.collection("Person")
                .get()
                .addOnSuccessListener { res ->
                    for (doc in res) {
                        val person: PersonModel = doc.toObject(PersonModel::class.java)
                        temp.add(person)
                    }
                    trySend(temp)
                }.addOnFailureListener {

                }
            awaitClose{
                close()
            }
        }
    }
    
    fun getTomb(): Flow<List<TombModel>> {
        return callbackFlow {
            val temp = mutableListOf<TombModel>()
            firebaseInstance.collection("Tomb")
                .get()
                .addOnSuccessListener { res ->
                    for (doc in res) {
                        val person: TombModel = doc.toObject(TombModel::class.java)
                        temp.add(person)
                    }
                    trySend(temp)
                }.addOnFailureListener {

                }
            awaitClose{
                close()
            }
        }
    }


}