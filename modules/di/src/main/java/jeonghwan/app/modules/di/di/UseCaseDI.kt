package jeonghwan.app.modules.di.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jeonghwan.app.domain.PersonRepositoryInterface
import jeonghwan.app.domain.TombRepositoryInterface
import jeonghwan.app.modules.di.usecase.PersonUseCaseImpl
import jeonghwan.app.modules.di.usecase.PersonUseCaseInterface
import jeonghwan.app.modules.di.usecase.TombUseCaseImpl
import jeonghwan.app.modules.di.usecase.TombUseCaseInterface
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