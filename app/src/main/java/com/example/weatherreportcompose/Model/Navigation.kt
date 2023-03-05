package com.example.weatherreportcompose.Model

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.weatherreportcompose.MainActivity
import com.example.weatherreportcompose.Model.DB.LocationsDB
import com.example.weatherreportcompose.Screen
import com.example.weatherreportcompose.ScreenHome
import com.example.weatherreportcompose.Screens.SearchScreen
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.ViewModel.ScreenSeven
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
@Composable
fun Navigation(application: Application){

    val navController = rememberNavController()
    val weatherViewModel: MainPageViewModel = viewModel(factory = MainPageViewModel.Factory)
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
            SearchScreen(weatherViewModel = weatherViewModel)
        }
    }
}