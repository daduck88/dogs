package com.duck.dogsapp.data.local

import com.duck.dogsapp.data.BreedImagesDataSource
import com.duck.dogsapp.data.local.db.AppDatabase
import com.duck.dogsapp.data.local.db.BreedImagesEntity

class BreedImagesLocalDataSource(private val appDatabase: AppDatabase) :
    BreedImagesDataSource {
    override suspend fun getBreedImages(breed: String): List<String> {
        return appDatabase.breedImagesDao().query(breed)?.let {
            it.images
        }?: emptyList()
    }

    override suspend fun saveBreedImages(breed: String, breedImages: List<String>) {
        appDatabase.breedImagesDao().insert(
            BreedImagesEntity(
                name = breed,
                images = breedImages
            )
        )
    }
}