package com.example.weatherreportcompose.Model.DataClasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)
data class Clouds(
    val all: Int
)
data class Rain(
    val lh: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Snow(
    val `1h`: Double
)
data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
data class Weather(var temp: Float,
                   var tempFeelsLike: Float,
                   var pressure: Float,
                   var description: String,
                   var dateTxt: String)
data class WeatherItem(
    val base: String = "",
    val clouds: Clouds = Clouds(-1),
    val cod: Int = 0,
    val coord: Coord = Coord(-1.1, -1.1),
    val dt: Int = -1,
    val id: Int = -1,
    val main: Main = Main(0.0,0,0,0,0,0.0,0.0,0.0,),
    val name: String = "",
    val snow: Snow = Snow( 0.0),
    val sys: Sys = Sys("", -1, -1),
    val timezone: Int = 0,
    val visibility: Int = 0,
    val weather: List<WeatherX> = listOf(WeatherX("", "" ,0, "")),
    val rain: Rain = Rain(1),
    val wind: Wind = Wind(-1, -1.1, -1.1),
    val dt_txt: String = "2022-08-30 15:00:00"
)
data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)
data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)

data class ForecastItem(
    val list: List<WeatherItem> = listOf(WeatherItem())
)