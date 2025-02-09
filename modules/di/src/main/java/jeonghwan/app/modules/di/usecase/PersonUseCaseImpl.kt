package jeonghwan.app.modules.di.usecase

import jeonghwan.app.modules.domain.PersonRepositoryInterface
import jeonghwan.app.modules.domain.entity.PersonEntity

class PersonUseCaseImpl (
    private val personRepository: PersonRepositoryInterface
) : PersonUseCaseInterface {
    override suspend fun getPersonAll(): Result<List<PersonEntity>> {
        return try {
            Result.success(personRepository.getPersonAll())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPerson(key: Int): Result<PersonEntity> {
        TODO("Not yet implemented")
    }
}