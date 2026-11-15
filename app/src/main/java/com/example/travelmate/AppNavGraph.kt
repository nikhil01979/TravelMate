package com.example.travelmate

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelmate.screens.DashboardScreen
import com.example.travelmate.screens.LoginScreen
import com.example.travelmate.screens.SignupScreen
import com.example.travelmate.screens.SplashScreen
import com.example.travelmate.viewmodel.AuthViewModel

// App navigation routes
object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
}

// Main navigation graph
@Composable
fun AppNavGraph(
    authViewModel: AuthViewModel
) {
    // Creates navigation controller
    val navController = rememberNavController()

    // Observes auth state
    val authState by authViewModel.authState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        // Splash screen
        composable(Routes.SPLASH) {
            SplashScreen(
                onSplashFinished = {
                    // Checks login status
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
            )
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
                }
            )
        }
    }
}