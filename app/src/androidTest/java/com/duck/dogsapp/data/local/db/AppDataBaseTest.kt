package com.duck.dogsapp.data.local.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AppDataBaseTest {

    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeBreedAndReadInList() = runTest {
        val breed = BreedEntity("dogo", emptyList())
        db.breedDao().insert(listOf(breed))
        val breedsResponse = db.breedDao().queryAll()
        assertThat(breedsResponse[0], equalTo(breed))
    }

    @Test
    @Throws(Exception::class)
    fun writeBreedImagesAndReadInList() = runTest {
        val dogBreed = "dogo"
        val breedImages = BreedImagesEntity(dogBreed, emptyList())
        db.breedImagesDao().insert(breedImages)
        val breedImagesResponse = db.breedImagesDao().query(dogBreed)
        assertThat(breedImagesResponse, equalTo(breedImages))
    }

}