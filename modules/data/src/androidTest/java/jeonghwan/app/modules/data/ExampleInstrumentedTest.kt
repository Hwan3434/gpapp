package jeonghwan.app.modules.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
class FirebaseDataSourceImplTest {

    private lateinit var firebaseDataSourceImpl: FirebaseDataSourceImpl

    @MockK
    lateinit var mockFirebase: FirebaseFirestore

    @MockK
    lateinit var mockPersonCollection: CollectionReference

    @MockK
    lateinit var mockTombCollection: CollectionReference

    @MockK
    lateinit var mockPersonSnapshot: QuerySnapshot

    @MockK
    lateinit var mockTombSnapshot: QuerySnapshot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        firebaseDataSourceImpl = FirebaseDataSourceImpl(mockFirebase)

        // Mock Firestore collections
        coEvery { mockFirebase.collection("Person") } returns mockPersonCollection
        coEvery { mockFirebase.collection("Tomb") } returns mockTombCollection
    }

    @Test
    fun `사람_가져오기`() = runTest {
        val mockPersonList = listOf(
            PersonModel(
                name = "John Doe",
                personKey = 1,
                generator = 1,
                etc = "",
                alive = true,
                family = "가평이씨",
                gender = true,
                spouse = null,
            ),
            PersonModel(
                name = "John Doe",
                personKey = 1,
                generator = 1,
                etc = "",
                alive = true,
                family = "가평이씨",
                gender = true,
                spouse = null,
            ),
        )

        // Mock the Firestore response
        coEvery { mockPersonCollection.get().await() } returns mockPersonSnapshot
        coEvery { mockPersonSnapshot.toObjects(PersonModel::class.java) } returns mockPersonList

        val result = firebaseDataSourceImpl.getPersonAll()
        assertEquals(mockPersonList, result)
    }

    @Test
    fun `무덤정보_가져오기`() = runTest {
        val mockTombList = listOf(
            TombModel(
                tombKey = 1,
                name = "1",
            ),
            TombModel(
                tombKey = 2,
                name = "2",
            ),
        )

        // Mock the Firestore response
        coEvery { mockTombCollection.get().await() } returns mockTombSnapshot
        coEvery { mockTombSnapshot.toObjects(TombModel::class.java) } returns mockTombList

        val result = firebaseDataSourceImpl.getTombAll()
        assertEquals(mockTombList, result)
    }
}