package com.example.mainactivity.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface FilmService {
    @GET("films/{id}")
    suspend fun findFilm(
        @Path("id") id: Int
    ):Film
    @GET("films")
    suspend fun findAllFilm(

    ):SevenFilms
    companion object {
        private const val BASE_URL = "https://swapi.py4e.com/api/"
        fun create(): FilmService{
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(FilmService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
            return retrofit.create(FilmService::class.java)
        }
    }
}