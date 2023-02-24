package com.example.weatherreportcompose.Model

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherreportcompose.MainActivity
import com.example.weatherreportcompose.Screen
import com.example.weatherreportcompose.ScreenHome
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.ViewModel.ScreenSeven

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            ScreenHome(navController = navController)
        }
        composable(
            route = Screen.SevenDays.route
        ){ backStackEntry ->
            ScreenSeven()
        }
    }
}