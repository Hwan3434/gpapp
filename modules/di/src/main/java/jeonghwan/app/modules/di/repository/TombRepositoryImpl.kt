package jeonghwan.app.modules.di.repository

import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.FirebaseDataSourceImpl
import jeonghwan.app.modules.data.model.TombModel
import jeonghwan.app.modules.di.common.toEntity
import jeonghwan.app.modules.domain.TombRepositoryInterface
import jeonghwan.app.modules.domain.entity.TombEntity
import javax.inject.Inject

class TombRepositoryImpl constructor(
    private val firebase: DataSourceInterface
) : TombRepositoryInterface {

    override suspend fun getTombAll(): List<TombEntity> {
        val tombModels: List<TombModel> = firebase.getTombAll()
        return tombModels.map { it.toEntity() }
    }

    override suspend fun getTomb(key: Int): TombEntity {
        TODO("Not yet implemented")
    }

}