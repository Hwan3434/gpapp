package jeonghwan.app.modules.domain

import jeonghwan.app.modules.domain.entity.PersonEntity

interface PersonRepositoryInterface {
    suspend fun getPersonAll(): List<PersonEntity>
    suspend fun getPerson(key: Int): PersonEntity
}