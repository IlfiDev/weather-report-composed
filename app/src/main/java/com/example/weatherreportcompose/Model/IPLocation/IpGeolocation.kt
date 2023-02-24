package com.example.weatherreportcompose.Model.IPLocation

data class IpGeolocation(
    val `as`: String = "",
    val city: String = "",
    val country: String = "",
    val countryCode: String = "",
    val isp: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val org: String = "",
    val query: String = "",
    val region: String = "",
    val regionName: String = "",
    val status: String = "",
    val timezone: String = "",
    val zip: String = ""
)
