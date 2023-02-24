package com.example.weatherreportcompose.Model.Retrofit

import com.example.weatherreportcompose.Model.CityApi.CitySuggestions
import com.example.weatherreportcompose.Model.IPLocation.IpGeolocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitCitySuggestionServices {


    @GET
    suspend fun getCitySuggestion(@Url url: String): Response<CitySuggestions>
}