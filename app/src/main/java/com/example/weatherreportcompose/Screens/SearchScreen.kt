package com.example.weatherreportcompose.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherreportcompose.Model.DB.Location
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.screenBackground

@Composable
fun SearchScreen(weatherViewModel: MainPageViewModel = viewModel()){
    screenBackground(weatherViewModel)

    val cities by weatherViewModel.cities.collectAsState()
    Column (horizontalAlignment = Alignment.CenterHorizontally){

        SearchText(weatherViewModel)
        DisplayCities(cities, weatherViewModel)
    }

}

@Composable
fun SearchText(viewModel: MainPageViewModel){
    val suggestions by viewModel.suggestion.collectAsState()
    var selectedText by remember { mutableStateOf("")}
    var expanded by remember { mutableStateOf(false)}
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)){

        OutlinedTextField(value = selectedText, onValueChange = {
            selectedText = it
            viewModel.getSuggestion(selectedText)
            expanded = true
            Log.e("City", suggestions.toString())
        },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textfieldSize = layoutCoordinates.size.toSize()
                }
            )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current){textfieldSize.width.toDp()}),
            properties = PopupProperties(focusable = false)
            )
        {
            suggestions.results.forEachIndexed{ itemIndex, label ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    viewModel.addCity(suggestions.results[itemIndex])
                }) {
                    Column(){
                        Text(text = label.city)
                        Text(text = label.country)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayCities(cities: List<Location>, viewModel: MainPageViewModel){

    LazyColumn{
        items(cities.toList()){
            item ->
            CitiesCard(city = item, viewModel)
        }

    }

}


@Composable
fun CitiesCard(city: Location, viewModel: MainPageViewModel){
    val name = city.name
    Card(
        modifier = Modifier
            .size(335.dp, 86.dp)
            .background(Color(0x19C9DBED)).clickable {  }
            .pointerInput(Unit){
                               detectTapGestures(onLongPress = {
                                   Log.w("A","AAAAAAAA")
                                   viewModel.removeCity(city)
                               })
            } ,
        shape = RoundedCornerShape(18.dp),
        elevation = 0.dp
    ){
        Column(modifier = Modifier.fillMaxHeight()){
            Text(name!!, modifier = Modifier.size(100.dp, 40.dp))
            Row{
                Text("28")
                Text("18")
            }
        }
    }
}
