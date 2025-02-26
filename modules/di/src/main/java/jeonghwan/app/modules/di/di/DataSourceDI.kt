package jeonghwan.app.modules.di.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jeonghwan.app.modules.data.DataSourceInterface
import jeonghwan.app.modules.data.FirebaseDataSourceImpl
import jeonghwan.app.modules.data.db.AppDatabase
import jeonghwan.app.modules.data.db.DATABASE_NAME
import jeonghwan.app.modules.data.db.PersonFavoriteDatasource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceDI {

    @Provides
    @Singleton
    fun provideFirebaseDataSource(): DataSourceInterface {
        return FirebaseDataSourceImpl(FirebaseFirestore.getInstance())
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFavoriteDao(appDatabase: AppDatabase): PersonFavoriteDatasource {
        return appDatabase.personFavoriteDatasource()
    }
}