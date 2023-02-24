package com.example.weatherreportcompose.Model.CityApi

data class CitySuggestionModel(
    val Name: String = "",
    val Country: String = "",
    val Region: String = "",
    val State: String = "",
    val City: String = "",
    val Lon: Double = 0.0,
    val Lat: Double = 0.0,
    val Formatted: String = ""

)
data class CitySuggestions(
    val Suggestions: List<CitySuggestionModel> = listOf(CitySuggestionModel())
)
