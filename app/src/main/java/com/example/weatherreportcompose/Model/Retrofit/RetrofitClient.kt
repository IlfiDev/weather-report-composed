package com.example.weatherreportcompose.Model.Retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    var retrofit : Retrofit? = null

    fun getClient(): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit!!
    }

}
val okHttpClient = OkHttpClient.Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .build()
object RetrofitIPLocationClient {
    var retrofit : Retrofit? = null

    fun getIpLocationClient(): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("http://ip-api.com/json/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit!!
    }
}

object RetrofitCityAPIClient {
    var retrofit : Retrofit? = null

    fun getCitYSuggestion(): Retrofit {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.geoapify.com/v1/geocode/autocomplete?text=")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
