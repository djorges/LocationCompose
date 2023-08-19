package com.example.locationcompose.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.locationcompose.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    context: Context,
    onClickStart: () -> Unit,
    onClickStop: () -> Unit
) {
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    //Single Permission Launcher
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permsMap ->
            val areGranted = permsMap.values.reduce { prev, next -> prev && next} // true && false && ..
            if(areGranted){
                //Start Location
                onClickStart()
            }else{
                //Show toast denied.
                Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val allPermissionsGranted = permissions.all {
                ContextCompat.checkSelfPermission( context, it) == PackageManager.PERMISSION_GRANTED
            }

            if(allPermissionsGranted){
                //Start location
                onClickStart()
            }else{
                multiplePermissionResultLauncher.launch(permissions)
            }
        }){
            Text(text = "Start")
        }
        Spacer( modifier = Modifier.height(16.dp))
        Button(
            onClick = onClickStop
        ){
            Text(text = "Stop")
        }
    }
}