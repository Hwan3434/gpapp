package jeonghwan.app.modules.di.repository

import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.di.common.toEntity
import jeonghwan.app.modules.domain.PersonRepositoryInterface
import jeonghwan.app.modules.domain.entity.PersonEntity

class PersonRepositoryImpl constructor(
    private val firebase: DataSourceInterface
) : PersonRepositoryInterface {

    override suspend fun getPersonAll(): List<PersonEntity> {
        val personModels: List<PersonModel> = firebase.getPersonAll()
        return personModels.map { it.toEntity() }
    }

    override suspend fun getPerson(key: Int): PersonEntity {
        TODO("Not yet implemented")
    }

}