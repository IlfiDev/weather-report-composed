package com.example.weatherreportcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import com.example.weatherreportcompose.Model.Navigation
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.ui.theme.WeatherReportComposeTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherReportComposeTheme {
                Navigation()
            }

        }

    }




}
@Composable
fun ScreenHome(weatherViewModel: MainPageViewModel = viewModel(), navController: NavController) {
    val weather by weatherViewModel.weather.collectAsState()
    val forecast by weatherViewModel.forecast.collectAsState()
    screenBackground()
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(weatherViewModel, navController)
        MainInfo(weather)
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            weatherInfoCard(weatherData = weather)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp, alignment = Alignment.Start)
            ) {
                val style = TextStyle(fontSize = 14.sp, color = Color.White)
                Text("today", style = style)
                Text("Tomorrow", style = style)
                Text("Next 7 days", style = style,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SevenDays.route)
                    }
                )
            }
            DisplayWeatherCards(forecastItem = forecast)
        }

    }
}



@Composable
fun MainInfo(weather: WeatherItem) {
    Column(
        modifier = Modifier
            .size(220.dp, 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(alignment = Alignment.TopCenter,
            painter = painterResource(id = R.drawable.cloud),
            contentDescription = null,
            modifier = Modifier.size(220.dp, 134.dp)
        )
        Text((weather.main.temp.toBigDecimal().toInt() - 273).toString(), style = TextStyle(fontSize = 72.sp, color = Color.White))
        Text(weather.weather.get(0).main, style = TextStyle(fontSize = 20.sp, color = Color.White))
        Text(weather.dt_txt, style = TextStyle(fontSize = 10.sp, color = Color.White))


    }
}

@Composable
fun DisplayWeatherCards(forecastItem: ForecastItem) {
    val hoursList = ArrayList<WeatherItem>()
    if(hoursList != null) {
        for (i in 0..forecastItem.list.size - 1) {
            hoursList.add(forecastItem.list.get(i))
        }
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),
    modifier = Modifier.padding(horizontal = 20.dp)) {
        items(hoursList) { arrItem ->
            hoursCard(hourData = arrItem)
        }
    }
}

@Preview
@Composable
fun screenBackground() {
    val colorStops = listOf(
        Color(0xFF45458B),
        Color(0xFF000000)

    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = colorStops))
    ) {
    }

}


@Composable
fun hoursCard(hourData: WeatherItem) {
    val temperature = hourData.main.temp - 273
    val time = hourData.dt_txt
    var resultTime = ""
    Log.e("Weather", time)
    val formattedTimeInt = time.split(" ")[1].split(":")[0].toInt()
    if(formattedTimeInt - 12 <= 0){
        resultTime = formattedTimeInt.toString() + " am"
    }else{
        resultTime = (formattedTimeInt - 12).toString() + " pm"
    }
    Card(
        modifier = Modifier
            .size(80.dp, 122.dp), shape = RoundedCornerShape(18.dp),
        backgroundColor = Color(0x19C9DBED),
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    resultTime,
                    style = TextStyle(color = Color(0xFF9BA0AB), fontSize = 14.sp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "weather_icon",
                    modifier = Modifier.size(24.dp),

                    )
                Text(
                    temperature.roundToInt().toBigDecimal().toString() + "°C",
                    style = TextStyle(color = Color.White, fontSize = 14.sp)
                )
            }

        }
    }
}

data class WeatherData(val windSpeed: Int, val humidity: Int, val riskOfRains: Int)

@Composable
fun weatherItems(value: String, imageId: Int, type: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(73.dp, 63.dp)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "weather_icon",
            modifier = Modifier.size(24.dp),
        )

        Text(value + "°", style = TextStyle(color = Color.White, fontSize = 14.sp))
        Text(type, style = TextStyle(color = Color(0xFF9BA0AB), fontSize = 10.sp))
    }
}

@Composable
fun weatherInfoCard(weatherData: WeatherItem) {
    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(91.dp),
        backgroundColor = Color(0x19C9DBED),
        elevation = 0.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            weatherItems(
                weatherData.wind.speed.toString(),
                imageId = R.drawable.ic_launcher_background,
                type = "wind"
            )
            weatherItems(
                value = weatherData.main.humidity.toString(),
                imageId = R.drawable.ic_launcher_background,
                type = "Humidity"
            )
            weatherItems(
                weatherData.rain.lh.toString() + "%",
                R.drawable.ic_launcher_background,
                "Rain"
            )
        }


    }

}


@Composable
fun Header(weatherViewModel: MainPageViewModel = viewModel(), navController: NavController) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_city),
            modifier = Modifier
                .size(32.dp)
                .clickable {
                   navController.navigate(Screen.SearchScreen.route)
                },
            contentDescription = "Add City"
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(weatherViewModel.location.collectAsState().value.city, style = TextStyle(color = Color.White, fontSize = 14.sp))
            Text("nav")
        }
        Image(
            painter = painterResource(id = R.drawable.more_button),
            modifier = Modifier
                .size(32.dp)
                .clickable { weatherViewModel.updateHome() },
            contentDescription = "More"
        )
    }

}