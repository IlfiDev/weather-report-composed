package com.example.weatherreportcompose.Model.CityApi

import com.google.gson.annotations.SerializedName

data class CitySuggestionModel(
    @SerializedName("name")val name: String = "",
    @SerializedName("country")val country: String = "",
    @SerializedName("region")val region: String = "",
    @SerializedName("state")val state: String = "",
    @SerializedName("city")val city: String = "",
    @SerializedName("lon")val lon: Double = 0.0,
    @SerializedName("lat")val lat: Double = 0.0,
    @SerializedName("formatted") val formatted: String = ""

)
data class CitySuggestions(
    @SerializedName("results") val results: List<CitySuggestionModel> = listOf(CitySuggestionModel())
)
