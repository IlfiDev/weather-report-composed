package com.example.weatherreportcompose.Model

import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.weatherreportcompose.BuildConfig
import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import com.example.weatherreportcompose.Model.Retrofit.RetrofitClient
import com.example.weatherreportcompose.Model.Retrofit.RetrofitServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CityWeatherRepository() {
    val APIKey = BuildConfig.API_KEY
    val apiServices = RetrofitClient.getClient().create(RetrofitServices::class.java)


    suspend fun getCurrentWeather(lat: Double, lon: Double, city: String = ""): Response<WeatherItem> {
        val response = apiServices.getCurrentWeatherList("weather?lat=${lat}&lon=${lon}&appid=${APIKey}")
        try {
            if (response.isSuccessful) {
                return response
            } else {
                Log.e("tag", "Error:${response.code()}")
            }
        } catch (e: HttpException) {
        }
        return response
    }

    suspend fun getWeatherForecast(lat: Double, lon: Double, city: String): Response<ForecastItem> {
        val response = apiServices.getFiveDaysWeather("forecast?lat=${lat}&lon=${lon}&appid=${APIKey}")
        try {
            if (response.isSuccessful) {
                return response
            } else {
                Log.e("Forecast", "Error:${response.code()}")
            }
        } catch (e: HttpException) {
        }
        return response
    }
}

