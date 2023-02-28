package com.example.weatherreportcompose.Model.CityApi

import android.util.Log
import com.example.weatherreportcompose.BuildConfig
import com.example.weatherreportcompose.Model.Retrofit.RetrofitCityAPIClient
import com.example.weatherreportcompose.Model.Retrofit.RetrofitCitySuggestionServices
import retrofit2.HttpException
import retrofit2.Response

class CitySuggestionRepository {
    val apiServices = RetrofitCityAPIClient.getCitYSuggestion().create(RetrofitCitySuggestionServices::class.java)

    val API_KEY = BuildConfig.CITY_API_KEY
    suspend fun getCitySuggestion(request: String): Response<CitySuggestions> {
        Log.e("Repos", request)
        val response = apiServices.getCitySuggestion("autocomplete?text=${request}&type=city&format=json&apiKey=${API_KEY}")
        try{
            if(response.isSuccessful){
                Log.e("Response", response.body().toString())
                return response
            }
            else{
                Log.e("Retrofit", "Error:${response.code()}")
            }
        }
        catch (e: HttpException){}
        return response
    }
}