package com.duck.dogsapp.data.local

import com.duck.dogsapp.data.BreedsDataSource
import com.duck.dogsapp.data.local.db.AppDatabase
import com.duck.dogsapp.data.local.db.BreedEntity
import com.duck.dogsapp.data.remote.objects.Breed

class BreedsLocalDataSource(private val appDatabase: AppDatabase) :
    BreedsDataSource {
    override suspend fun getBreeds(): List<Breed> {
        return appDatabase.breedDao().queryAll().map { breedEntity ->
            with(breedEntity) {
                Breed(
                    name = name,
                    subBreed = subBreed
                )
            }
        }
    }

    override suspend fun saveBreeds(breeds: List<Breed>) {
        breeds.map { breed ->
            with(breed) {
                BreedEntity(
                    name = name,
                    subBreed = subBreed
                )
            }
        }.let {
            appDatabase.breedDao().insert(it)
        }
    }
}