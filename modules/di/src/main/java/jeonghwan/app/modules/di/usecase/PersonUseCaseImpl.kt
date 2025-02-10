package jeonghwan.app.modules.di.usecase

import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.domain.PersonRepositoryInterface
import jeonghwan.app.entity.PersonEntity

class PersonUseCaseImpl (
    private val personRepository: PersonRepositoryInterface
) : PersonUseCaseInterface {

    override suspend fun getPersonPaging(page: DocumentSnapshot?, size: Int): Result<Pair<List<PersonEntity>, DocumentSnapshot?>> {
        return try {
            Result.success(personRepository.getPersonPaging(page, size))
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPersonAll(): Result<List<PersonEntity>> {
        return try {
            Result.success(personRepository.getPersonAll())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getPerson(key: Int): Result<PersonEntity?> {
        return try {
            Result.success(personRepository.getPerson(key))
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}