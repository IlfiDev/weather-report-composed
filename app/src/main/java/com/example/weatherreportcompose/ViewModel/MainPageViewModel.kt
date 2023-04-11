package com.example.weatherreportcompose.ViewModel

import android.app.Application
import android.content.Context
import android.os.Debug
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.example.weatherreportcompose.MainActivity
import com.example.weatherreportcompose.Model.CityApi.CitySuggestionModel
import com.example.weatherreportcompose.Model.CityApi.CitySuggestionRepository
import com.example.weatherreportcompose.Model.CityApi.CitySuggestions
import com.example.weatherreportcompose.Model.CityWeatherRepository
import com.example.weatherreportcompose.Model.DB.*
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
import javax.inject.Inject

class MainPageViewModel(private val application: Application) : AndroidViewModel(application) {

    val weatherRepository = CityWeatherRepository()
    val locationRepository = IpLocationRepository()
    val suggestionRepository = CitySuggestionRepository()
    private var locationsRepository: LocationsRepository
    private val _location = MutableStateFlow(IpGeolocation())

    private val _weather = MutableStateFlow(WeatherItem())
    val weather = _weather.asStateFlow()

    private val _forecast = MutableStateFlow(ForecastItem())
    val forecast = _forecast.asStateFlow()

    private val _suggestion = MutableStateFlow(CitySuggestions())
    val suggestion = _suggestion.asStateFlow()

    val home = Location(1, "home", "home", 0.0, 0.0)
    private val _citiesDB = MutableStateFlow(listOf(home))
    val cities = _citiesDB.asStateFlow()

    private var position = -1

    init {
        updateHome()
        val locationsDao = LocationsDatabase.getInstance(application).locationDao()
        locationsRepository = LocationsRepository(locationsDao)
        getCities()

    }

    fun moveToNext(){
        if(position + 1 < _citiesDB.value.size){

            position++
            Log.w("Pos", position.toString())
            val city = _citiesDB.value[position]
            val lat = city.lat
            val lon = city.lon
            val name = city.name

            Log.e("cityName", name)
            updateWeather(lat!!, lon!!, name!!)
            updateForecast(lat, lon, name)
        }



    }
    fun moveToPrevious(){
        position--
        Log.w("Pos", position.toString())
        if(position >= 0){

            val city = _citiesDB.value[position]
            val lat = city.lat
            val lon = city.lon
            val name = city.name
            Log.e("cityName", name)
            updateWeather(lat!!, lon!!, name!!)
            updateForecast(lat, lon, name)
        }
        else{
            position = -1
            updateHome()
        }

    }
    fun removeCity(location: Location) {
        CoroutineScope(Dispatchers.IO).launch{
            locationsRepository.removeLocation(location)
            getCities()
        }
    }

    fun getCities(){
        CoroutineScope(Dispatchers.IO).launch{
            val cities = locationsRepository.getLocations()
            Log.w("DB", _citiesDB.toString())
            withContext(Dispatchers.Main){
                _citiesDB.value = cities
            }
        }
    }
    fun updateHome(){
        CoroutineScope(Dispatchers.IO).launch{

            var loc = IpGeolocation()
            val job = launch {
                val response = locationRepository.getLocation()
                Log.e("IP", response.body().toString())

                _location.value = response.body()!!
                loc = _location.value
                Log.e("Loc", loc.toString())
            }
            job.join()
            launch {
                launch{
                    updateWeather(loc.lat, loc.lon, loc.city)
                }
                launch{
                    updateForecast(loc.lat, loc.lat, loc.city)
                }
            }
        }
    }


    fun updateWeather(lat:Double, lon: Double, city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = weatherRepository.getCurrentWeather(lat, lon, city)
            Log.e("Weather", response.body().toString())
            withContext(Dispatchers.Main) {
                _weather.value = response.body()!!
            }
        }
    }


    fun updateForecast(lat:Double, lon: Double, city: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = weatherRepository.getWeatherForecast(lat, lon, city)
            Log.e("Forecast", response.body().toString())
            withContext(Dispatchers.Main){
                _forecast.value = response.body()!!
            }
        }
    }



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


    fun addCity(suggestion: CitySuggestionModel){
        val city = Location(name = suggestion.name, lat = suggestion.lat, lon = suggestion.lon, country = suggestion.country)
        CoroutineScope(Dispatchers.IO).launch {

            locationsRepository.addLocation(city)

            getCities()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return MainPageViewModel(
                    application
                ) as T
            }
        }
    }

}