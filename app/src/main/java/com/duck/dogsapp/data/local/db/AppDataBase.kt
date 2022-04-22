package com.duck.dogsapp.data.local.db

import android.content.Context
import androidx.room.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(
    entities = [BreedEntity::class, BreedImagesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao
    abstract fun breedImagesDao(): BreedImagesDao

    companion object {
        fun create(appContext: Context) = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database_dogs"
        ).build()

    }
}

class Converters {
    @TypeConverter
    fun fromList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)
}