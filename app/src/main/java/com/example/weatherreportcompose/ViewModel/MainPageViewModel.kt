package com.example.weatherreportcompose.ViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherreportcompose.Model.CityWeatherRepository
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPageViewModel() : ViewModel() {
    val weatherRepository = CityWeatherRepository()
    private val _weather = MutableStateFlow(WeatherItem())
    val weather = _weather.asStateFlow()
    fun updateWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherRepository.getCurrentWeather("Moscow", _weather)
            withContext(Dispatchers.Main) {
                _weather.value = response.body()!!
            }
        }
    }

}