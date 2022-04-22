package com.duck.dogsapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BreedImagesRepositoryImp(
    private val remoteDataSource: BreedImagesDataSource,
    private val localDataSource: BreedImagesDataSource
) : BreedImagesRepository {
    override suspend fun getBreedImages(breed: String): Flow<List<String>> = flow {
        emit(localDataSource.getBreedImages(breed))
        delay(10)
        val remoteResponse = remoteDataSource.getBreedImages(breed)
        emit(remoteResponse)
        localDataSource.saveBreedImages(breed, remoteResponse)
    }.flowOn(Dispatchers.IO)
}

interface BreedImagesRepository {
    suspend fun getBreedImages(breed: String): Flow<List<String>>
}