package com.example.weatherreportcompose.Model.IPLocation

import android.util.Log
import com.example.weatherreportcompose.Model.Retrofit.RetrofitClient
import com.example.weatherreportcompose.Model.Retrofit.RetrofitIPLocationClient
import com.example.weatherreportcompose.Model.Retrofit.RetrofitIpLocationServieces
import com.example.weatherreportcompose.Model.Retrofit.RetrofitServices
import retrofit2.HttpException
import retrofit2.Response

class IpLocationRepository {
    val apiServices = RetrofitIPLocationClient.getIpLocationClient().create(RetrofitIpLocationServieces::class.java)


    suspend fun getLocation(): Response<IpGeolocation> {
        val response = apiServices.getIpLocation("")
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
}