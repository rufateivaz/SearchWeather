package com.sample.weather.screens.permission

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sample.weather.screens.NavigationItem
import java.util.Locale

/**
 * This screen is presented to user to ask for location permission.
 * */
@SuppressLint("MissingPermission")
@Composable
fun LocationPermissionScreen(navController: NavController) {
    val context = LocalContext.current
    var city: String? by remember { mutableStateOf(null) }
    var permissionGranted by remember { mutableStateOf(false) }
    var permissionDenied by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permissionGranted = isGranted
        permissionDenied = !isGranted
    }

    when {
        permissionGranted -> {
            city?.let {
                LaunchedEffect(Unit) {
                    navController.navigate("${NavigationItem.Search.route}/$it") {
                        popUpTo(NavigationItem.LocationPermission.route) { inclusive = true }
                    }
                }
            } ?: run {
                val fusedLocationClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    city = location?.let {
                        val geocoder = Geocoder(context, Locale.getDefault())
                        geocoder.getFromLocation(it.latitude, it.longitude, 1)
                            ?.let { addresses ->
                                if (addresses.isNotEmpty()) addresses[0].locality ?: ""
                                else ""
                            } ?: ""
                    } ?: ""
                }
            }

        }

        permissionDenied -> {
            LaunchedEffect(Unit) {
                navController.navigate("${NavigationItem.Search.route}/") {
                    popUpTo(NavigationItem.LocationPermission.route) { inclusive = true }
                }
            }
        }

        else -> {
            LaunchedEffect(Unit) {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}
