package com.duck.dogsapp.data.remote.objects

import com.google.gson.annotations.SerializedName

data class DogsResponse<T>(
    @SerializedName("message")
    val response: T,
    val status: String
)
