package com.example.weatherreportcompose

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object SevenDays : Screen("seven_days_screen")

    object SearchScreen : Screen("search_screen")
}
