package jeonghwan.app.modules.di.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.FirebaseDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceDI {

    @Provides
    @Singleton
    fun provideFirebaseDataSource(): DataSourceInterface {
        return FirebaseDataSourceImpl(FirebaseFirestore.getInstance())
    }

}