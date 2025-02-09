package jeonghwan.app.modules.di.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jeonghwan.app.modules.di.repository.PersonRepositoryImpl
import jeonghwan.app.modules.di.usecase.PersonUseCaseImpl
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseImpl
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
import jeonghwan.app.modules.domain.PersonRepositoryInterface
import jeonghwan.app.modules.domain.TombRepositoryInterface
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseDI {

    @Provides
    @Singleton
    fun providePersonUseCase(
        personRepositoryInterface: PersonRepositoryInterface
    ): PersonUseCaseInterface {
        return PersonUseCaseImpl(personRepository = personRepositoryInterface)
    }


    @Provides
    @Singleton
    fun provideTombUseCase(
        tombRepositoryInterface: TombRepositoryInterface
    ): TombUseCaseInterface {
        return TombUseCaseImpl(tombRepository = tombRepositoryInterface)
    }
}