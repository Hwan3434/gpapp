package jeonghwan.app.modules.di.usecase

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.firestore.DocumentSnapshot
import jeonghwan.app.domain.PersonRepositoryInterface
import jeonghwan.app.domain.model.GenderType
import jeonghwan.app.domain.model.PersonEntity
import jeonghwan.app.modules.data.db.PersonData
import jeonghwan.app.modules.data.db.PersonFavoriteDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PersonUseCaseImpl(
    private val personRepository: PersonRepositoryInterface,
    private val personFavoriteDatasource: PersonFavoriteDatasource
) : PersonUseCaseInterface {
    override suspend fun insert(person: PersonEntity) {
        personFavoriteDatasource.insert(
            PersonData(
                key = person.key,
                alive = person.alive,
                name = person.name,
                genderType = person.genderType == GenderType.Female,
                family = person.family
            )
        )
    }

    override fun getPagedFavorites(): Flow<PagingData<PersonEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                personFavoriteDatasource.getPagedFavorites()
            }
        ).flow.map { pagingData ->
            Log.e("PersonUseCaseImpl", "getPagedFavorites")
            pagingData.map {
                Log.e("PersonUseCaseImpl", "person : ${it.name}")
                PersonEntity(
                    key = it.key,
                    alive = it.alive,
                    name = it.name,
                    genderType = if (it.genderType) GenderType.Female else GenderType.Male,
                    family = it.family,
                    tombKey = null,
                    spouse = null,
                    generator = null,
                    clan = null,
                    etc = null,
                    dateDeath = null,
                    father = null,
                    mather = null
                )
            }
        }
    }

    override fun getFavorites(): Flow<List<PersonEntity>> {
        return personFavoriteDatasource.getFavorites()
            .map { favoriteDataList ->
                favoriteDataList.map {
                    PersonEntity(
                        key = it.key,
                        alive = it.alive,
                        name = it.name,
                        genderType = if (it.genderType) GenderType.Female else GenderType.Male,
                        spouse = null,
                        father = null,
                        mather = null,
                        clan = null,
                        etc = null,
                        generator = null,
                        dateDeath = null,
                        family = it.family,
                        tombKey = null,
                    )
                }
            }
    }

    override suspend fun isFavorite(personKey: Int): Boolean {
        return personFavoriteDatasource.isPersonKeyExists(personKey) > 0
    }

    override suspend fun delete(personKey: Int) {
        personFavoriteDatasource.delete(personKey)
    }

    override suspend fun getPersonPaging(page: DocumentSnapshot?, size: Int): Result<Pair<List<PersonEntity>, DocumentSnapshot?>> {
        return try {
            Result.success(personRepository.getPersonPaging(page, size))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPersonAll(): Result<List<PersonEntity>> {
        return try {
            Result.success(personRepository.getPersonAll())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPerson(key: Int): Result<PersonEntity?> {
        return try {
            Result.success(personRepository.getPerson(key))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}