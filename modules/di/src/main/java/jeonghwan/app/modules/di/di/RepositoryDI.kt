package jeonghwan.app.modules.di.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jeonghwan.app.domain.PersonRepositoryInterface
import jeonghwan.app.domain.TombRepositoryInterface
import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.di.repository.PersonRepositoryImpl
import jeonghwan.app.modules.di.repository.TombRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryDI {

    @Provides
    @Singleton
    fun bindPersonRepository(
        firebaseProvider: DataSourceInterface
    ): PersonRepositoryInterface {
        return PersonRepositoryImpl(firebase = firebaseProvider)
    }


    @Provides
    @Singleton
    fun bindTombRepository(
        firebaseProvider: DataSourceInterface
    ): TombRepositoryInterface {
        return TombRepositoryImpl(firebase = firebaseProvider)
    }
}