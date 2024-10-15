package com.sample.weather.feature.search

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sample.domain.search.model.SearchDataState
import com.sample.weather.navigation.NavigationItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Search screen, where user types query (city name) and performs search operation.
 * */
@Composable
fun SearchScreen(
    navController: NavController,
    city: String = "",
    viewModel: SearchViewModel = hiltViewModel()
) {
    if (!viewModel.initialSearchResultPresented) {
        if (city.isEmpty()) {
            viewModel.getSearchDataWithOldQuery()
        } else {
            viewModel.getSearchData(city)
        }
    }
    val query = rememberSaveable { mutableStateOf("") }
    val searchDataState: SearchDataState by viewModel.searchDataState

    LaunchedEffect(searchDataState) {
        if (searchDataState is SearchDataState.Success) {
            val searchData = (searchDataState as SearchDataState.Success).searchData
            val searchDataJson = Uri.encode(Json.encodeToString(searchData))
            val route = "${NavigationItem.SearchDetails.route}/$searchDataJson"
            navController.navigate(route = route)
            viewModel.initSearchDataState()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(50.dp)
            ),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.background
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.background
            ),
            value = query.value,
            onValueChange = { query.value = it },
            placeholder = {
                Text(
                    color = MaterialTheme.colorScheme.background,
                    text = "Write a city name; e.g., London"
                )
            }
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            onClick = { viewModel.getSearchData(query.value) },
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.inversePrimary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            enabled = query.value.isNotEmpty() && (searchDataState !is SearchDataState.Loading)
        ) {
            if (searchDataState is SearchDataState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp).testTag("progressIndicator"),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.background
                )
            } else {
                Text(text = "Search")
            }
        }

        if (searchDataState is SearchDataState.Error) {
            Text(
                text = "The query is not successful.",
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

