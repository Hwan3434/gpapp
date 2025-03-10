package jeonghwan.app.modules.di.repository

import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.domain.PersonRepositoryInterface
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.di.common.toEntity

class PersonRepositoryImpl (
    private val firebase: DataSourceInterface
) : PersonRepositoryInterface {

    override suspend fun getPersonPaging(
        page: DocumentSnapshot?, size: Int
    ): Pair<List<PersonEntity>, DocumentSnapshot?> {
        val (models, lastVisible) = firebase.getPersonPaging(page, size)
        val entities = models.map { it.toEntity() }
        return entities to lastVisible
    }

    override suspend fun getPersonAll(): List<PersonEntity> {
        val personModels: List<PersonModel> = firebase.getPersonAll()
        return personModels.map { it.toEntity() }
    }

    override suspend fun getPerson(key: Int): PersonEntity? {
        val model: PersonModel? = firebase.getPerson(key)
        return model?.toEntity()
    }

    override suspend fun getPersonChild(key: Int): List<PersonEntity> {
        val personModels: List<PersonModel> = firebase.getPersonChild(key)
        return personModels.map { it.toEntity() }
    }

}