package com.example.weatherreportcompose.Model.Retrofit

import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitServices {
    @GET
    suspend fun getCurrentWeatherList(@Url url: String): Response<WeatherItem>

    @GET
    suspend fun getFiveDaysWeather(@Url url: String): Response<ForecastItem>

}
