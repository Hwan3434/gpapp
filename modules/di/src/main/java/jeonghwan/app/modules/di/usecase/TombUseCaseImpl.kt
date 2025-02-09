package jeonghwan.app.modules.di.usecase

import jeonghwan.app.modules.domain.TombRepositoryInterface
import jeonghwan.app.modules.domain.entity.TombEntity

class TombUseCaseImpl (
    private val tombRepository: TombRepositoryInterface
) : TombUseCaseInterface {
    override suspend fun getTombAll(): Result<List<TombEntity>> {
        return try {
            Result.success(tombRepository.getTombAll())
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getTomb(key: Int): Result<TombEntity> {
        TODO("Not yet implemented")
    }
}