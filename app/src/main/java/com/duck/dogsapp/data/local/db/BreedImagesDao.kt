package com.duck.dogsapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BreedImagesDao {
    @Query("select * from breed_images where name = :breed")
    suspend fun query(breed: String): BreedImagesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breedImages: BreedImagesEntity)
}