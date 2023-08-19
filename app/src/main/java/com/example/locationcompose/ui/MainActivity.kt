package com.example.locationcompose.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.locationcompose.data.LocationService
import com.example.locationcompose.ui.screen.MainScreen
import com.example.locationcompose.ui.theme.LocationComposeTheme
import com.example.locationcompose.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LocationComposeTheme {
                MainScreen(
                    viewModel = viewModel,
                    context = applicationContext,
                    onClickStart = {startLocation()},
                    onClickStop = {stopLocation()}
                )
            }
        }
    }

    private fun startLocation(){
        Intent(
            applicationContext,
            LocationService::class.java
        ).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }
    private fun stopLocation(){
        Intent(
            applicationContext,
            LocationService::class.java
        ).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
    }
}