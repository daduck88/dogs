package com.duck.dogsapp.data.remote

import com.duck.dogsapp.data.BreedImagesDataSource

class BreedImagesRemoteDataSource(private val apiService: APIService) : BreedImagesDataSource {
    override suspend fun getBreedImages(breed: String): List<String> {
        val response = apiService.getBreedImages(breed)
        if (response.status != "success") {
            throw Exception("Something when wrong")
        }
        return response.response
    }

    override suspend fun saveBreedImages(breed: String, breeds: List<String>) {
        throw Exception("API data source doesn't insert Data")
    }
}