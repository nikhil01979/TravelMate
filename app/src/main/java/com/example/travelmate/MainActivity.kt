package com.example.travelmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelmate.AppNavGraph
import com.example.travelmate.viewmodel.AuthViewModel

// Main app activity
class MainActivity : ComponentActivity() {

    // Starts the app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    // Creates auth model
                    val authViewModel: AuthViewModel = viewModel()

                    // Opens navigation graph
                    AppNavGraph(
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}