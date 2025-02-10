package jeonghwan.app.modules.di.usecase

import jeonghwan.app.entity.TombEntity

interface TombUseCaseInterface {
    suspend fun getTombAll(): Result<List<TombEntity>>
    suspend fun getTomb(key: Int): Result<TombEntity>
}