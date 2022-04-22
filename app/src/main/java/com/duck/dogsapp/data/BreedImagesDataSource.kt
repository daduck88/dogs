package com.duck.dogsapp.data

import com.duck.dogsapp.data.remote.objects.Breed

interface BreedImagesDataSource {
    suspend fun getBreedImages(breed: String) : List<String>
    suspend fun saveBreedImages(breed: String, breeds: List<String>)
}