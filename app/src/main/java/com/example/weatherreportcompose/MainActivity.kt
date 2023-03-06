package com.example.weatherreportcompose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherreportcompose.Model.DataClasses.ForecastItem
import com.example.weatherreportcompose.Model.DataClasses.WeatherItem
import com.example.weatherreportcompose.Model.Navigation
import com.example.weatherreportcompose.ViewModel.MainPageViewModel
import com.example.weatherreportcompose.ui.theme.WeatherReportComposeTheme
import com.vk.api.sdk.*
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException
import com.vk.api.sdk.internal.ApiCommand
import com.vk.api.sdk.requests.VKRequest
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint
import com.vk.sdk.api.photos.dto.PhotosGetAlbumsResponse
import com.vk.sdk.api.users.dto.UsersGetNameCase
import com.vk.sdk.api.users.dto.UsersSubscriptionsItem
import java.util.*
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fingerprints = getCertificateFingerprint(this, this.packageName)
        Log.w("fingerprint", fingerprints?.get(0)!!)
        VK.login(this, arrayListOf(VKScope.PHOTOS, VKScope.WALL, VKScope.AUDIO))
        val call = VKMethodCall.Builder()
            .method("users.get")
            .args("fields", "photo_200")
            .version("v5.131")
            .build()

        val request = VKApiManager.execute
        VK.execute(request = PhotosGetAlbumsResponse())
        setContent {
            WeatherReportComposeTheme {
                Navigation(application)
            }

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val callback = object: VKAuthCallback{
            override fun onLogin(token: VKAccessToken){
                Log.i("Auth", "Success")
            }

            override fun onLoginFailed(authException: VKAuthException) {
                Log.e("Auth", "Failed")
            }
        }
        if (data == null || !VK.onActivityResult(requestCode = requestCode, resultCode, data, callback)){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}


val images = mapOf(
    "Thunderstorm" to R.drawable.property_1_thunderstorm_b,
    "Drizzle" to R.drawable.property_1_sun_and_rain_b,
    "Rain" to R.drawable.property_1_heavy_rain_b,
    "Snow" to R.drawable.property_1_snow_b,
    "Mist" to R.drawable.property_1_cloudy_b,
    "Smoke" to R.drawable.cloud,
    "Haze" to R.drawable.cloud,
    "Dust" to R.drawable.cloud,
    "Fog" to R.drawable.cloud,
    "Ash" to R.drawable.cloud,
    "Squall" to R.drawable.cloud,
    "Tornado" to R.drawable.cloud,
    "Clear" to R.drawable.property_1_sun_b,
    "Clouds" to R.drawable.property_1_mostly_cloudy_b
)
val smallImages = mapOf(
    "Thunderstorm" to R.drawable.condition_thunderstorm,
    "Drizzle" to R.drawable.condition_rain_opportunity,
    "Rain" to R.drawable.condition_rain,
    "Snow" to R.drawable.condition_snow,
    "Mist" to R.drawable.condition_cloudy,
    "Smoke" to R.drawable.condition_cloudy,
    "Haze" to R.drawable.condition_cloudy,
    "Dust" to R.drawable.condition_cloudy,
    "Fog" to R.drawable.condition_cloudy,
    "Ash" to R.drawable.condition_cloudy,
    "Squall" to R.drawable.condition_cloudy,
    "Tornado" to R.drawable.condition_tornado,
    "Clear" to R.drawable.condition_sunny,
    "Clouds" to R.drawable.condition_cloudy
)
@Composable
fun ScreenHome(weatherViewModel: MainPageViewModel, navController: NavController) {
    val weather by weatherViewModel.weather.collectAsState()
    val forecast by weatherViewModel.forecast.collectAsState()
    screenBackground(weatherViewModel)
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
    val image: Int
    if(weather.weather[0].main == ""){
        image = R.drawable.cloud
    }else{
        image = images[weather.weather[0].main]!!
    }
    Column(
        modifier = Modifier
            .size(220.dp, 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(alignment = Alignment.TopCenter,
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(330.dp, 150.dp)
        )
        Text((weather.main.temp.toBigDecimal().toInt() - 273).toString(), style = TextStyle(fontSize = 72.sp, color = Color.White))
        Text(weather.weather.get(0).description, style = TextStyle(fontSize = 20.sp, color = Color.White))
        Text(Calendar.getInstance().time.toString(), style = TextStyle(fontSize = 10.sp, color = Color.White))


    }
}

@Composable
fun DisplayWeatherCards(forecastItem: ForecastItem) {
    val weatherItem: List<WeatherItem>
    if (forecastItem.list.size == 1){
       weatherItem = listOf(WeatherItem())
    }
    else{
        weatherItem = forecastItem.list.slice(0..7)
    }
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp),
    modifier = Modifier.padding(horizontal = 20.dp)) {
        items(weatherItem) { arrItem ->
            hoursCard(hourData = arrItem)
        }
    }
}
@Composable
fun screenBackground(viewModel: MainPageViewModel) {
    var drag = 0.0f

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
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectDragGestures(onDrag = { change, dragAmount ->
                change.consume()
                drag = dragAmount.x

            },
                onDragEnd = {
                    if (drag < 0) {

                        viewModel.moveToNext()
                    } else if (drag > 0) {
                        viewModel.moveToPrevious()
                    }
                    drag = 0.0f
                })
//            when{
//                y > 0 ->{
//                    Log.e("Swipe", "Swipe Up")
//                }
//                y < 0 -> {
//
//                    Log.e("Swipe", "Swipe down")}
//            }
        }
        .fillMaxSize())
    Surface(modifier = Modifier.pointerInput(Unit){
        detectDragGestures { change, dragAmount ->
            change.consume()
            val(x,y) = dragAmount
            when{
                dragAmount.x > 0 -> {

                   viewModel.moveToPrevious()
                    Log.e("Swipe", "Swipe right")
                }
                dragAmount.x < 0 -> {

                    viewModel.moveToNext()
                    Log.e("Swipe", "Swipe left")
                }
            }
        }
    }
    ){}

}



@Composable
fun hoursCard(hourData: WeatherItem) {
    val image: Int
    if(hourData.weather[0].main == ""){
        image = R.drawable.condition_cloudy
    }else{
        image = smallImages[hourData.weather[0].main]!!
    }
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
                    painter = painterResource(id = image),
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
                imageId = R.drawable.condition_wind,
                type = "wind"
            )
            weatherItems(
                value = weatherData.main.humidity.toString(),
                imageId = R.drawable.condition_humidity,
                type = "Humidity"
            )
            weatherItems(
                weatherData.rain.lh.toString() + "%",
                R.drawable.condition_rain_opportunity,
                "Rain"
            )
        }


    }

}


@Composable
fun Header(weatherViewModel: MainPageViewModel, navController: NavController) {
    val city = weatherViewModel.weather.collectAsState().value.name
    val context = LocalContext.current
    val weather by weatherViewModel.weather.collectAsState()
    val temp = (weather.main.temp.toBigDecimal().toInt() - 273).toString() + "°C"
    val weatherState = weather.weather[0].description
    val text = "It's ${temp} and ${weatherState} in ${city}"
    val sendIntent = Intent.createChooser(Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        putExtra(Intent.EXTRA_TITLE, "AAAA")
        type = "image/*"
    }, null)
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
            Text(city , style = TextStyle(color = Color.White, fontSize = 14.sp))
            Text("nav")
        }
        Image(
            painter = painterResource(id = R.drawable.more_button),
            modifier = Modifier
                .size(32.dp)
                .clickable { context.startActivity(sendIntent) },
            contentDescription = "More"
        )
    }

}