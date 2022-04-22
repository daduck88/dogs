package com.duck.dogsapp.data

import com.duck.dogsapp.data.remote.objects.Breed

interface BreedsDataSource {
    suspend fun getBreeds() : List<Breed>
    suspend fun saveBreeds(breeds: List<Breed>)
}