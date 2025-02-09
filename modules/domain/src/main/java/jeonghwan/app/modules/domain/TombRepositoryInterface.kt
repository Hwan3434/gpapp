package jeonghwan.app.modules.domain

import jeonghwan.app.modules.domain.entity.TombEntity

interface TombRepositoryInterface {
    suspend fun getTombAll(): List<TombEntity>
    suspend fun getTomb(key: Int): TombEntity
}