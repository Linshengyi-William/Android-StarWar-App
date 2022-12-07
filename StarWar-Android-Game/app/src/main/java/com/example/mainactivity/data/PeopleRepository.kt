package com.example.mainactivity.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PeopleRepository (
    private val service: PeopleService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        suspend fun loadPeopleSearch(id: Int): Result<People> =
            withContext(ioDispatcher) {
                try {


                    val results = service.findPerson(id)
                    Log.d("results for findPerson", results.toString())
                    Result.success(results)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
        suspend fun loadAllPeopleSearch(): Result<AllPeople> =
            withContext(ioDispatcher) {
                try {
                    val results = service.findAllPeople()
                    Result.success(results)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }