package com.example.weatherreportcompose.Model.Retrofit

import com.example.weatherreportcompose.Model.IPLocation.IpGeolocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitIpLocationServieces {

    @GET
    suspend fun getIpLocation(@Url url: String): Response<IpGeolocation>
}