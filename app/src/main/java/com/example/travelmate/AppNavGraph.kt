package com.example.travelmate

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.travelmate.screens.*
import com.example.travelmate.viewmodel.AuthViewModel
import com.example.travelmate.viewmodel.ExploreViewModel

// Navigation routes
object Routes {

    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val EXPLORE = "explore"
    const val SAVED_PLACES = "saved_places"
}

// Main navigation
@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel
) {

    // Navigation controller
    val navController = rememberNavController()

    // Authentication state
    val authState by authViewModel.authState.collectAsState()

    // Shared ViewModel
    val exploreViewModel: ExploreViewModel = viewModel()

    // Navigation host
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Splash screen
        composable(Routes.SPLASH) {
            SplashScreen {
                if (authState.isAuthenticated) {

                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }

                } else {

                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            }
        }

        // Login screen
        composable(Routes.LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,

                onLoginSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },

                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        // Registration screen
        composable(Routes.REGISTER) {
            SignupScreen(
                authViewModel = authViewModel,

                onRegistrationSuccess = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.REGISTER) {
                            inclusive = true
                        }
                    }
                },

                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // Dashboard screen
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                authViewModel = authViewModel,

                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.DASHBOARD) {
                            inclusive = true
                        }
                    }
                },

                onExploreClick = {
                    navController.navigate(Routes.EXPLORE)
                },

                onSavedPlacesClick = {
                    navController.navigate(Routes.SAVED_PLACES)
                }
            )
        }

        // Explore screen
        composable(Routes.EXPLORE) {
            ExploreScreen(
                exploreViewModel = exploreViewModel,

                onBackClick = {
                    navController.popBackStack()
                },

                onSavedPlacesClick = {
                    navController.navigate(Routes.SAVED_PLACES)
                }
            )
        }

        // Saved places screen
        composable(Routes.SAVED_PLACES) {
            SavedPlacesScreen(
                exploreViewModel = exploreViewModel,

                onBackClick = {
                    navController.popBackStack()
                },

                onPlaceSelected = {
                    navController.navigate(Routes.EXPLORE) {
                        popUpTo(Routes.SAVED_PLACES) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}