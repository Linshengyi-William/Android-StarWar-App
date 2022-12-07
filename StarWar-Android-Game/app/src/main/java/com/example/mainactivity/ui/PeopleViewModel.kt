package com.example.mainactivity.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mainactivity.data.*
import kotlinx.coroutines.launch

class PeopleViewModel :  ViewModel() {
    private val repository = PeopleRepository(PeopleService.create())
    private val _searchResults = MutableLiveData<People?>(null)
    val searchResults: LiveData<People?> = _searchResults
    private val _allPeopleSearchResults = MutableLiveData<AllPeople?>(null)
    val allPeopleSearchResults: LiveData<AllPeople?> = _allPeopleSearchResults
    private val _fighterOneResults = MutableLiveData<People?>(null)
    val fighterOneResults: LiveData<People?> = _fighterOneResults
    private val _fighterTwoResults = MutableLiveData<People?>(null)
    val fighterTwoResults: LiveData<People?> = _fighterTwoResults
    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadPeopleResults(query: Int) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadPeopleSearch(query)
            Log.d("Blue","$result")
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
    fun loadAllPeopleResults() {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadAllPeopleSearch()
            Log.d("Blue","$result")
            _allPeopleSearchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
    fun loadFighterResults(fighterOneId: Int, fighterTwoId: Int) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val resultOne = repository.loadPeopleSearch(fighterOneId)
            val resultTwo = repository.loadPeopleSearch(fighterTwoId)
            Log.d("Blue","$resultOne $resultTwo")
            _fighterOneResults.value = resultOne.getOrNull()
            _fighterTwoResults.value = resultTwo.getOrNull()
            _loadingStatus.value = when (resultOne.isSuccess and resultTwo.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}