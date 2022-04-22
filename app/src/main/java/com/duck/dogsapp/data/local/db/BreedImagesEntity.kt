package com.duck.dogsapp.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_images")
data class BreedImagesEntity(
    @PrimaryKey val name: String,
    val images: List<String>
)