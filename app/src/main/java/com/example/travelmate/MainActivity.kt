package com.example.travelmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.snapshots.Snapshot
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelmate.BuildConfig
import com.example.travelmate.viewmodel.AuthViewModel
import com.google.android.libraries.places.api.Places

// Main activity
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check Maps key
        check(BuildConfig.MAPS_API_KEY.isNotBlank()) {
            "Missing Google Maps API key in local.properties"
        }

        // Initialize Places SDK
        Places.initializeWithNewPlacesApiEnabled(
            applicationContext,
            BuildConfig.MAPS_API_KEY
        )

        setContent {
            MaterialTheme {
                Surface {
                    // Create auth viewmodel
                    val authViewModel: AuthViewModel = viewModel()

                    AppNavGraph(
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}