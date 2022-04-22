package com.duck.dogsapp.data.remote

import com.duck.dogsapp.data.BreedsDataSource
import com.duck.dogsapp.data.remote.objects.Breed
import javax.inject.Inject

class BreedsRemoteDataSource @Inject constructor(private val apiService: APIService) :
    BreedsDataSource {
    override suspend fun getBreeds(): List<Breed> {
        val response = apiService.getBreeds()
        if (response.status != "success") {
            throw Exception("Something when wrong")
        }
        val breeds = ArrayList<Breed>()
        response.response.map {
            breeds.add(Breed(it.key, it.value))
        }
        return breeds
    }

    override suspend fun saveBreeds(breeds: List<Breed>) {
        throw Exception("API data source doesn't insert Data")
    }
}