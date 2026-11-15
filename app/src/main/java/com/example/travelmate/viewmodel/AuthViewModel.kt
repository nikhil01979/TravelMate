package com.example.travelmate.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Authentication data
data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class AuthViewModel : ViewModel() {

    // Firebase authentication
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Stores auth state
    private val _authState = MutableStateFlow(
        AuthState(
            isAuthenticated = auth.currentUser != null
        )
    )
    val authState: StateFlow<AuthState> = _authState

    // Registers new users
    fun register(email: String, password: String) {
        // Checks empty email
        if (email.isBlank()) {
            _authState.value = AuthState(
                errorMessage = "Please enter your email address."
            )
            return
        }
        // Validates email format
        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            _authState.value = AuthState(
                errorMessage = "Please enter a valid email address."
            )
            return
        }
        // Checks password length
        if (password.length < 6) {
            _authState.value = AuthState(
                errorMessage = "Password must contain at least 6 characters."
            )
            return
        }
        // Starts registration
        _authState.value = AuthState(isLoading = true)
        auth.createUserWithEmailAndPassword(
            email.trim(),
            password
        ).addOnCompleteListener { task ->
            // Checks registration result
            if (task.isSuccessful) {

                _authState.value = AuthState(
                    isAuthenticated = true,
                    successMessage = "Account created successfully."
                )
            } else {
                _authState.value = AuthState(
                    errorMessage = task.exception?.message
                        ?: "Registration failed."
                )
            }
        }
    }

    // Logs in users
    fun login(email: String, password: String) {
        // Checks empty fields
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState(
                errorMessage = "Please enter your email and password."
            )
            return
        }
        // Starts login
        _authState.value = AuthState(isLoading = true)
        auth.signInWithEmailAndPassword(
            email.trim(),
            password
        ).addOnCompleteListener { task ->
            // Checks login result
            if (task.isSuccessful) {
                _authState.value = AuthState(
                    isAuthenticated = true,
                    successMessage = "Login successful."
                )

            } else {
                _authState.value = AuthState(
                    errorMessage = task.exception?.message
                        ?: "Login failed."
                )
            }
        }
    }

    // Logs out users
    fun logout() {
        auth.signOut()
        _authState.value = AuthState(
            isAuthenticated = false,
            successMessage = "You have been logged out."
        )
    }

    // Clears messages
    fun clearMessage() {
        _authState.value = _authState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }
}