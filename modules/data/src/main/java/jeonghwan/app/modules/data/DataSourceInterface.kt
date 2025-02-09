package jeonghwan.app.modules.data

import jeonghwan.app.modules.data.model.PersonModel
import jeonghwan.app.modules.data.model.TombModel

interface DataSourceInterface {
    suspend fun getPersonAll(): List<PersonModel>
    suspend fun getTombAll(): List<TombModel>
    suspend fun getPerson(key: Int): PersonModel
    suspend fun getTomb(key: Int): TombModel
}