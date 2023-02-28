package com.example.weatherreportcompose.ViewModel

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherreportcompose.*
import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.Main
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherX
import com.example.weatherreportcompose.R
import com.example.weatherreportcompose.ui.theme.WeatherReportComposeTheme


@Composable
fun ScreenSeven(weatherViewModel: MainPageViewModel = viewModel(), navController: NavController){
    val weatherItem = weatherViewModel.weather.collectAsState().value
    val forecastItem = weatherViewModel.forecast.collectAsState().value

    screenBackground()
    Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(20.dp)){

        Header(weatherViewModel, navController = navController)
        SevenDaysMainInfo(weatherItem = weatherItem)
        weatherInfoCard(weatherData = weatherItem)
        DaysColumn(forecastItem)
    }

    //weatherInfoCard(weatherData = )
}
@Composable
fun DaysColumn(forecastItem: ForecastItem){

    for(item in forecastItem.list){

    }
    LazyColumn {
        items(forecastItem.list) { arrItme ->
            DayCard(weatherInfo = arrItme)
        }
    }

}
@Preview
@Composable
fun showSevenDays(){
    val a = WeatherItem(weather = listOf(WeatherX("aaaaaaa", "aaaaaa", 0,"aaaaa")),main = Main(0.0, 1, 1, 1, 1, -3.0, 0.0, 0.0))
    SevenDaysMainInfo(weatherItem = a)
}
@Composable
fun SevenDaysMainInfo(weatherItem: WeatherItem){
    Row(modifier = Modifier
        .height(103.dp)
        .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(modifier = Modifier
            .size(129.dp, 103.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start){
            Text((weatherItem.main.temp.toBigDecimal().toInt()-273).toString() + "°",
            style = TextStyle(fontSize = 58.sp, color = Color.White), )

            Text(weatherItem.weather.get(0).main,
            style = TextStyle(fontSize = 14.sp, color = Color(0xFF9BA0AB)))
        }
        Image(painter = painterResource(id = R.drawable.cloud), contentDescription = null,
        modifier = Modifier.size(126.dp)
            )
    }

}
@Composable
fun DayCard(weatherInfo: WeatherItem){
    val max_temp = (weatherInfo.main.temp_max.toBigDecimal().toInt()-273).toString()
    val min_temp = (weatherInfo.main.temp_min.toBigDecimal().toInt()-273).toString()

    Row(modifier = Modifier
        .height(52.dp)
        .fillMaxWidth()
        .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){

        Text("today", style = TextStyle(fontSize = 17.sp, color = Color.White),
        modifier = Modifier.size(80.dp, 20.dp))
        Spacer(modifier = Modifier.width(24.dp))
        Row(modifier = Modifier.size(60.dp, 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Text(max_temp, style = TextStyle(fontSize = 14.sp, color = Color.White),)
            Text(min_temp, style = TextStyle(fontSize = 14.sp, color = Color(0xFF9BA0AB)))
        }

        Spacer(modifier = Modifier.width(152.dp))
        Image(painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.size(24.dp),

        )
    }

}

@Preview
@Composable
fun DayCardPreview(){
    DayCard(WeatherItem(main = Main(1.1, 1, 1, 1, 1, 1.0, 10.0, -10.0)))
}
