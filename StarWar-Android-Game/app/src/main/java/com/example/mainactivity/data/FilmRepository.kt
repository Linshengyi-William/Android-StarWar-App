package com.example.mainactivity.data
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
class FilmRepository (
    private val service: FilmService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadFilmSearch(id: Int): Result<Film> =
        withContext(ioDispatcher) {
            try {
                val results = service.findFilm(id)
                Result.success(results)
            } catch(e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun loadAllFilmSearch(): Result<SevenFilms> =
        withContext(ioDispatcher) {
            try {
                val results = service.findAllFilm()
                Result.success(results)
            } catch(e: Exception) {
                Result.failure(e)
            }
        }
}