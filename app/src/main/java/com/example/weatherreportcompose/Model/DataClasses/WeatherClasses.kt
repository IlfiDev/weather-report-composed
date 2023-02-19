package com.example.weatherreportcompose.Model.DataClasses

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
    @SerializedName ("base") val base: String = "",
    @SerializedName ("clouds") val clouds: Clouds = Clouds(-1),
    @SerializedName ("cod") val cod: Int = 0,
    @SerializedName ("coord") val coord: Coord = Coord(-1.1, -1.1),
    @SerializedName ("dt") val dt: Int = -1,
    @SerializedName ("id") val id: Int = -1,
    @SerializedName ("main") val main: Main = Main(0.0,0,0,0,0,0.0,0.0,0.0,),
    @SerializedName ("name") val name: String = "",
    @SerializedName ("snow") val snow: Snow = Snow( 0.0),
    @SerializedName ("sys") val sys: Sys = Sys("", -1, -1),
    @SerializedName ("timezone") val timezone: Int = 0,
    @SerializedName ("visibility") val visibility: Int = 0,
    @SerializedName ("weather") val weather: List<WeatherX> = emptyList(),
    @SerializedName ("wind") val wind: Wind = Wind(-1, -1.1, -1.1),
    @SerializedName ("dt_txt") val dt_txt: String = ""
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
