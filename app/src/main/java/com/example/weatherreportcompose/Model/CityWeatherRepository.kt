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

class CityWeatherRepository(){
    val APIKey = BuildConfig.API_KEY
    val apiServices = RetrofitClient.getClient().create(RetrofitServices::class.java)


    sealed class Result<out R> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
    }

    suspend fun getCurrentWeather(city: String, weatherCallback: MutableStateFlow<WeatherItem>) : Response<WeatherItem>{
        val response = apiServices.getCurrentWeatherList("weather?q=${city}&appid=${APIKey}")
            try{
                if (response.isSuccessful){
                    return response
                }
                else{
                    Log.e("tag","Error:${response.code()}")
                }
            }catch(e: HttpException){}
        return response
    }

    suspend fun getWeatherForecast(city: String) : Response<ForecastItem>{
        val response = apiServices.getFiveDaysWeather("forecast?q=${city}&appid=${APIKey}")
        try{
            if(response.isSuccessful){
                return response
            }
            else{
                Log.e("Forecast", "Error:${response.code()}")
            }
        }
        catch (e: HttpException){}
        return response
    }
//        call.enqueue(object : Callback<WeatherItem> {
//            override fun onResponse(call : Call<WeatherItem>, response : Response<WeatherItem>){
//                Log.d("Weather", response.body().toString())
//                if(response.isSuccessful) {
//                    return response.body()!!
//                    Log.d("Weather", response.body().toString())
//                }
//
//            }
//            override fun onFailure(call : Call<WeatherItem>, t: Throwable){
//                Log.e("WeatherError", t.toString() )
//            }
//        })
}
//    fun getWeatherForTheWeek(city: String){
//        val call = apiServices.getFiveDaysWeather("forecast?q=${city}&appid=${APIKey}")
//        call.enqueue(object  : Callback<ForecastItem>{
//            override fun onResponse(call : Call<ForecastItem>, response : Response<ForecastItem>){
//                if(response.isSuccessful){
//                    return response.body()!!
//                    Log.d("Weather", response.body().toString())
//                }
//
//            }
//            override fun onFailure(call : Call<ForecastItem>, t: Throwable){
//                Log.e("WeatherError", t.toString() )
//            }
//
//        })
//    }


