package com.example.mainactivity.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.data.*
import kotlinx.coroutines.launch
class FilmViewModel : ViewModel(){
    private val repository = FilmRepository(FilmService.create())
    private val _searchResults = MutableLiveData<Film?>(null)
    val searchResults: LiveData<Film?> = _searchResults
    private val _allFilmSearchResults = MutableLiveData<SevenFilms?>(null)
    val allFilmSearchResults: LiveData<SevenFilms?> = _allFilmSearchResults
    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus
    //private val _filmQuestionResults = MutableLiveData<Film?>(null)
    //val filmQuestionResults = LiveData<Film?> = _filmQuestionResults

    fun loadFilmResults(query: Int){
        viewModelScope.launch{
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadFilmSearch(query)
            Log.d("Red", "$result")
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess){
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }

    fun loadAllFilmResults() {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadAllFilmSearch()
            Log.d("Red", "$result")
            _allFilmSearchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}