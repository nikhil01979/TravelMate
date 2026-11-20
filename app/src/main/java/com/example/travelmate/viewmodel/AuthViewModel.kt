package com.example.travelmate.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

// Authentication ViewModel
class AuthViewModel : ViewModel() {

    // Firebase instance
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Authentication state
    private val _authState = MutableStateFlow(
        AuthState(
            isAuthenticated = auth.currentUser != null
        )
    )

    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Register new users
    fun register(email: String, password: String) {

        // Validate email
        if (email.isBlank()) {
            _authState.value = AuthState(
                errorMessage = "Please enter your email address."
            )
            return
        }

        // Check email format
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim())
                .matches()
        ) {
            _authState.value = AuthState(
                errorMessage = "Please enter a valid email address."
            )
            return
        }

        // Validate password
        if (password.length < 6) {
            _authState.value = AuthState(
                errorMessage = "Password must contain at least 6 characters."
            )
            return
        }

        // Set loading state
        _authState.value = AuthState(isLoading = true)

        // Create Firebase account
        auth.createUserWithEmailAndPassword(
            email.trim(),
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                // Registration success
                _authState.value = AuthState(
                    isAuthenticated = true,
                    successMessage = "Account created successfully."
                )

            } else {

                // Registration failure
                _authState.value = AuthState(
                    errorMessage = task.exception?.message
                        ?: "Registration failed."
                )
            }
        }
    }

    // Login existing users
    fun login(email: String, password: String) {

        // Validate credentials
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState(
                errorMessage = "Please enter your email and password."
            )
            return
        }

        // Set loading state
        _authState.value = AuthState(isLoading = true)

        // Authenticate user
        auth.signInWithEmailAndPassword(
            email.trim(),
            password
        ).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                // Login success
                _authState.value = AuthState(
                    isAuthenticated = true,
                    successMessage = "Login successful."
                )

            } else {

                // Login failure
                _authState.value = AuthState(
                    errorMessage = task.exception?.message
                        ?: "Login failed."
                )
            }
        }
    }

    // Logout current user
    fun logout() {

        auth.signOut()

        // Reset authentication
        _authState.value = AuthState(
            isAuthenticated = false,
            successMessage = "You have been logged out."
        )
    }

    // Clear status messages
    fun clearMessage() {

        _authState.value = _authState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}