package com.example.weatherreportcompose.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainPageViewModel : ViewModel() {
    private val _location = MutableStateFlow<String>("")
    val location: StateFlow<String> = _location

    fun onLocationChanged(location: String){
        _location.value = location
    }
}