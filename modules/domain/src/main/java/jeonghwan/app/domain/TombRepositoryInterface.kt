package jeonghwan.app.domain

import jeonghwan.app.entity.TombEntity

interface TombRepositoryInterface {
    suspend fun getTombAll(): List<TombEntity>
    suspend fun getTomb(key: Int): TombEntity?
}