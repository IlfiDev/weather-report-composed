package com.example.weatherreportcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherreportcompose.R
import com.example.weatherreportcompose.*
import com.example.weatherreportcompose.ui.theme.WeatherReportComposeTheme
import androidx.compose.foundation.clickable

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherReportComposeTheme {
                screenBackground()
                Column (verticalArrangement = Arrangement.spacedBy(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                    Header()
                    val cardData = HourData("13 am", R.drawable.ic_launcher_background, "32 C")
                    val weatherData = WeatherData(10, 50, 80)
                    weatherInfoCard(weatherData = weatherData)
                    DisplayWeatherCards()
                }
            }

        }

    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()


}
@Composable
fun DisplayWeatherCards(){
    val array = listOf(HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        HourData("13 am", R.drawable.ic_launcher_background, "32 C"),
        )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){
        items(array) {
            arrItem -> hoursCard(hourData = arrItem)
        }
    }
}
@Preview
@Composable
fun screenBackground() {
    val colorStops = listOf(
    Color("#3C3C72".toColorInt()),
    Color("#000000".toColorInt())
)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(colors = colorStops))
    ){
        Header()
    }

}
data class HourData(val time: String, val icon: Int, val temperature: String)

@Composable
fun hoursCard(hourData: HourData){
    Card(
                modifier = Modifier
                .size(80.dp, 122.dp),shape = RoundedCornerShape(18.dp),
        backgroundColor = Color(0xFF2D3335)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
            ,
            contentAlignment = Alignment.TopCenter,
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(hourData.time, style = TextStyle(color = Color(0xFF9BA0AB), fontSize = 14.sp))
                Image(
                    painter = painterResource(id = hourData.icon),
                    contentDescription = "weather_icon",
                    modifier = Modifier.size(24.dp),

                )
                Text(hourData.temperature, style = TextStyle(color = Color.White, fontSize = 14.sp))
            }

        }
    }
}
data class WeatherData(val windSpeed: Int, val humidity: Int, val riskOfRains: Int)

@Composable
fun weatherItems(value: String, imageId: Int, type: String){
    Column(verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(73.dp, 63.dp)
            .fillMaxSize()){
        Image(
            painter = painterResource(id = imageId),
            contentDescription = "weather_icon",
            modifier = Modifier.size(24.dp),
        )

        Text(value, style = TextStyle(color = Color.White, fontSize = 14.sp))
        Text(type ,style = TextStyle(color = Color(0xFF9BA0AB), fontSize = 10.sp))
    }
}

@Composable
fun weatherInfoCard(weatherData: WeatherData){
    Card(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(91.dp),
        backgroundColor = Color(0xFF2D3335)
    ){
        Row(horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            weatherItems(
                weatherData.windSpeed.toString(),
                imageId = R.drawable.ic_launcher_background,
                type = "wind"
            )
            weatherItems(
                value = weatherData.humidity.toString(),
                imageId = R.drawable.ic_launcher_background,
                type = "Humidity"
            )
            weatherItems(
                weatherData.riskOfRains.toString(),
                R.drawable.ic_launcher_background,
                "Rain"
            )
        }


    }

}

val cardData = HourData("13 am", R.drawable.ic_launcher_background, "32 C")

@Preview
@Composable
fun Header(){
    Row(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = painterResource(id = R.drawable.add_city),
            modifier = Modifier
                .size(32.dp)
                .clickable { },
            contentDescription = "Add City"
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text("California", style = TextStyle(color = Color.White, fontSize = 14.sp))
            Text("nav")
        }
        Image(painter = painterResource(id = R.drawable.more_button),
            modifier = Modifier
                .size(32.dp)
                .clickable { },
            contentDescription = "More"
        )
    }

}

@Preview("aboba")
@Composable
fun weatherDataPreview(){

    val weatherData = WeatherData(10, 50, 80)
    weatherInfoCard(weatherData = weatherData)
}

@Preview("CardPreview")
@Composable
fun hourCardPreview(){
    hoursCard(hourData = cardData)
}