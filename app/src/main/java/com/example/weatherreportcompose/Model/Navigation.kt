package com.example.weatherreportcompose.Model

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherreportcompose.MainActivity
import com.example.weatherreportcompose.Screen
import com.example.weatherreportcompose.ScreenHome
import com.example.weatherreportcompose.Screens.SearchScreen
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.ViewModel.ScreenSeven

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val weatherViewModel: MainPageViewModel = viewModel()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            ScreenHome(navController = navController, weatherViewModel = weatherViewModel)
        }
        composable(
            route = Screen.SevenDays.route
        ){ backStackEntry ->
            ScreenSeven(weatherViewModel = weatherViewModel, navController = navController)
        }

        composable(route = Screen.SearchScreen.route){
            navBackStackEntry ->
            SearchScreen()
        }
    }
}