package com.example.weatherreportcompose.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherreportcompose.Model.CityWeatherRepository
import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import com.example.weatherreportcompose.Model.IPLocation.IpGeolocation
import com.example.weatherreportcompose.Model.IPLocation.IpLocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainPageViewModel() : ViewModel() {

    val weatherRepository = CityWeatherRepository()
    val locationRepository = IpLocationRepository()
    private val _location = MutableStateFlow(IpGeolocation())
    val location = _location.asStateFlow()

    private val _weather = MutableStateFlow(WeatherItem())
    val weather = _weather.asStateFlow()

    private val _forecast = MutableStateFlow(ForecastItem())
    val forecast = _forecast.asStateFlow()

    init {
        updateHome()
    }
    fun updateHome() = runBlocking {
        updateCurrentLocation()
    }
    suspend fun updateCurrentLocation() = coroutineScope {
        val job = launch {
            val response = locationRepository.getLocation()
            Log.e("IP", response.body().toString())
            _location.value = response.body()!!
        }
        job.join()
        launch {
            launch{updateWeather(_location.value.city)}
            launch{updateForecast(_location.value.city)}
        }
//        job.join()
//        launch{updateWeather(_location.value.city)}
//        launch{updateForecast(_location.value.city)}
//        updateAll(_location.value.city)
    }
    fun updateAll(city: String){
        updateWeather(city)
        updateForecast(city)
    }
    suspend fun updateWeatherC(city: String) = coroutineScope{
        val response = weatherRepository.getCurrentWeather(city)
        Log.e("Weather", response.body().toString())
        withContext(Dispatchers.Main) {
            _weather.value = response.body()!!
        }
    }
    suspend fun updateForecastC(city: String) = coroutineScope {
        val response = weatherRepository.getWeatherForecast(city)
        Log.e("Forecast", response.body().toString())
        withContext(Dispatchers.Main){
            _forecast.value = response.body()!!
        }
    }
    fun updateWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherRepository.getCurrentWeather(city)
            Log.e("Weather", response.body().toString())
            withContext(Dispatchers.Main) {
                _weather.value = response.body()!!
            }
        }
    }


    fun updateForecast(city: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = weatherRepository.getWeatherForecast(city)
            Log.e("Forecast", response.body().toString())
            withContext(Dispatchers.Main){
                _forecast.value = response.body()!!
            }
        }
    }

}