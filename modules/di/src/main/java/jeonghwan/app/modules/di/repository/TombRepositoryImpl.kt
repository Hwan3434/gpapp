package jeonghwan.app.modules.di.repository

import jeonghwan.app.domain.TombRepositoryInterface
import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.model.TombModel
import jeonghwan.app.modules.di.common.toEntity
import jeonghwan.app.domain.model.TombEntity

class TombRepositoryImpl (
    private val firebase: DataSourceInterface
) : TombRepositoryInterface {

    override suspend fun getTombAll(): List<TombEntity> {
        val tombModels: List<TombModel> = firebase.getTombAll()
        return tombModels.mapNotNull { it.toEntity() }
    }

    override suspend fun getTomb(key: Int): TombEntity? {
        val model: TombModel? = firebase.getTomb(key)
        return model?.toEntity()
    }

}