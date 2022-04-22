package com.duck.dogsapp.di

import android.content.Context
import com.duck.dogsapp.data.*
import com.duck.dogsapp.data.local.db.AppDatabase
import com.duck.dogsapp.data.local.BreedImagesLocalDataSource
import com.duck.dogsapp.data.local.BreedsLocalDataSource
import com.duck.dogsapp.data.remote.APIService
import com.duck.dogsapp.data.remote.BreedImagesRemoteDataSource
import com.duck.dogsapp.data.remote.BreedsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Singleton
    @Provides
    fun provideAPIService() = APIService.create()

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context) = AppDatabase.create(appContext)

    @Provides
    fun provideBreedsRepository(
        @Named("remoteBreedsDatasource")
        remoteDataSource: BreedsDataSource,
        @Named("localBreedsDatasource")
        localDataSource: BreedsDataSource,
    ): BreedsRepository {
        return BreedsRepositoryImp(remoteDataSource, localDataSource)
    }

    @Provides
    @Named("remoteBreedsDatasource")
    fun provideRemoteBreedsDataSource(
        apiService: APIService
    ): BreedsDataSource {
        return BreedsRemoteDataSource(apiService)
    }

    @Provides
    @Named("localBreedsDatasource")
    fun provideLocalBreedsDataSource(
        appDatabase: AppDatabase
    ): BreedsDataSource {
        return BreedsLocalDataSource(appDatabase)
    }

    @Provides
    fun provideBreedImagesRepository(
        @Named("remoteBreedImagesDatasource")
        remoteDataSource: BreedImagesDataSource,
        @Named("localBreedImagesDatasource")
        localDataSource: BreedImagesDataSource
    ): BreedImagesRepository {
        return BreedImagesRepositoryImp(remoteDataSource, localDataSource)
    }

    @Provides
    @Named("remoteBreedImagesDatasource")
    fun provideRemoteBreedImagesDataSource(
        apiService: APIService
    ): BreedImagesDataSource {
        return BreedImagesRemoteDataSource(apiService)
    }

    @Provides
    @Named("localBreedImagesDatasource")
    fun provideLocalBreedImagesDataSource(
        appDatabase: AppDatabase
    ): BreedImagesDataSource {
        return BreedImagesLocalDataSource(appDatabase)
    }
}
