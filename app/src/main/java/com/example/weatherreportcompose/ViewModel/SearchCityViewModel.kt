package com.example.weatherreportcompose.ViewModel

import androidx.lifecycle.ViewModel
import com.example.weatherreportcompose.Model.CityApi.CitySuggestionRepository
import com.example.weatherreportcompose.Model.CityApi.CitySuggestions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchCityViewModel : ViewModel() {

    val suggestionRepository = CitySuggestionRepository()
    private val _suggestion = MutableStateFlow(CitySuggestions())
    val suggestion = _suggestion.asStateFlow()

    fun getSuggestion(request: String) = runBlocking {
        CoroutineScope(Dispatchers.IO).launch{
            updateSuggstion(request)
        }
    }

    suspend fun updateSuggstion(request: String) {
        if ( request.length >=3){

                val response = suggestionRepository.getCitySuggestion(request)
                _suggestion.value = response.body()!!
        }

    }
}