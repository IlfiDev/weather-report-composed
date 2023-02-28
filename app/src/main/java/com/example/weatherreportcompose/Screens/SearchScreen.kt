package com.example.weatherreportcompose.Screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherreportcompose.Screen
import com.example.weatherreportcompose.ViewModel.SearchCityViewModel
import com.example.weatherreportcompose.screenBackground

@Composable
fun SearchScreen(searchCityViewModel: SearchCityViewModel = viewModel()){
    screenBackground()
    SearchText(searchCityViewModel)
}

@Composable
fun SearchText(viewModel: SearchCityViewModel){
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
            modifier = Modifier.fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    textfieldSize = layoutCoordinates.size.toSize()
                }
            )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false },
        modifier = Modifier.width(with(LocalDensity.current){textfieldSize.width.toDp()}),
            properties = PopupProperties(focusable = false)
            )
        {
            suggestions.results.forEach{ label ->
                DropdownMenuItem(onClick = { expanded = false}) {
                    Text(text = label.city)
                    Text(text = label.country)

                }
            }
        }
    }
}
