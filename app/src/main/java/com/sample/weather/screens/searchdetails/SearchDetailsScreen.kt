package com.sample.weather.screens.searchdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sample.domain.model.SearchData
import com.sample.weather.R

/**
 * Search Details screen, to show the details of the search result.
 * */
@Composable
fun SearchDetailsScreen(
    navController: NavController,
    searchData: SearchData
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            IconButton(
                modifier = Modifier.testTag("backButton"),
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Go back to search screen.",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = searchData.city,
                fontFamily = FontFamily.Serif,
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "${searchData.temperature}°F",
                fontFamily = FontFamily.Serif,
                fontSize = 56.sp,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row {
                Text(
                    text = searchData.description,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.surface
                )
                AsyncImage(
                    modifier = Modifier.size(32.dp),
                    model = searchData.icon,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "L:${searchData.lowTemp}° H:${searchData.highTemp}°",
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.padding(12.dp))

            val range = if (searchData.lowTemp < searchData.highTemp)
                " Today, it is ranged from ${searchData.lowTemp}° to ${searchData.highTemp}°."
            else ""

            val detailedDesc = "In ${searchData.city}, " +
                    "the temperature is ${searchData.temperature}°, " +
                    "and feels like ${searchData.feelsLike}°." +
                    range +
                    " Currently, ${searchData.description} can be observed," +
                    " and the humidity is ${searchData.humidity}."

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                text = detailedDesc,
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchDetailsScreenPreview() {
    val navController = rememberNavController()

    val searchData = SearchData(
        city = "Arlington",
        temperature = 92,
        lowTemp = 90,
        highTemp = 94,
        feelsLike = 94,
        humidity = 41,
        description = "clear sky",
        icon = "https://openweathermap.org/img/wn/10d@2x.png"
    )
    SearchDetailsScreen(
        navController = navController,
        searchData = searchData
    )
}
