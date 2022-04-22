package com.duck.dogsapp.data

import com.duck.dogsapp.data.remote.objects.Breed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BreedsRepositoryImp(
    private val remoteDataSource: BreedsDataSource,
    private val localDataSource: BreedsDataSource,
) : BreedsRepository {
    override suspend fun getBreeds(): Flow<List<Breed>> = flow {
        emit(localDataSource.getBreeds())
        // this give enough time to emit at least once (needed just when it's an error)
        delay(10)
        val remoteResponse = remoteDataSource.getBreeds()
        emit(remoteResponse)
        localDataSource.saveBreeds(remoteResponse)
    }.flowOn(Dispatchers.IO)
}

interface BreedsRepository {
    suspend fun getBreeds(): Flow<List<Breed>>
}