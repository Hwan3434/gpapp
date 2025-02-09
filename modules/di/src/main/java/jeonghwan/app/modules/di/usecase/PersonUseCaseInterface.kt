package jeonghwan.app.modules.di.usecase

import jeonghwan.app.modules.domain.entity.PersonEntity

interface PersonUseCaseInterface {
    suspend fun getPersonAll(): Result<List<PersonEntity>>
    suspend fun getPerson(key: Int): Result<PersonEntity>
}