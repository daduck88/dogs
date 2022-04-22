package com.duck.dogsapp.data.remote

import com.duck.dogsapp.data.remote.objects.DogsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("breed/{breed}/images")
    suspend fun getBreedImages(@Path("breed") breed: String): DogsResponse<List<String>>

    @GET("breeds/list/all")
    suspend fun getBreeds(): DogsResponse<HashMap<String, List<String>>>

    companion object {
        private const val BASE_URL = "https://dog.ceo/api/"
        fun create(): APIService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
            return retrofit.create(APIService::class.java)

        }
    }
}