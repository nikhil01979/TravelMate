package com.example.travelmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmate.viewmodel.AuthViewModel

// colors
private val Navy = Color(0xFF062B68)
private val Orange = Color(0xFFFF7900)
private val Gray = Color(0xFF718096)

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    // Observes auth state
    val authState by authViewModel.authState.collectAsState()

    // Stores user email
    var email by remember { mutableStateOf("") }

    // Stores password
    var password by remember { mutableStateOf("") }

    // Controls password visibility
    var passwordVisible by remember { mutableStateOf(false) }

    // Checks login status
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) onLoginSuccess()
    }

    // Login background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFFF0F7FF))
                )
            )
    ) {
        // Responsive screen layout
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            // Main login content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = maxHeight)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Welcome heading
                Text(
                    "Welcome Back!",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    "Your next adventure starts here.",
                    fontSize = 14.sp,
                    color = Gray
                )

                Spacer(Modifier.height(30.dp))

                // Login form
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        "Login to Your Account",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        "Enter your details to continue",
                        fontSize = 13.sp,
                        color = Gray
                    )

                    Spacer(Modifier.height(20.dp))

                    // Email field
                    Text(
                        "Email Address",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, null, tint = Navy)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Color(0xFFDCE3ED),
                            cursorColor = Orange
                        )
                    )

                    Spacer(Modifier.height(14.dp))

                    // Password field
                    Text(
                        "Password",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy
                    )

                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null, tint = Navy)
                        },
                        trailingIcon = {
                            // Toggles visibility
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = "Toggle password visibility",
                                    tint = Gray
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Navy,
                            unfocusedBorderColor = Color(0xFFDCE3ED),
                            cursorColor = Orange
                        )
                    )

                    Spacer(Modifier.height(20.dp))

                    // Login button
                    Button(
                        onClick = {
                            authViewModel.login(email.trim(), password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !authState.isLoading &&
                                email.isNotBlank() && password.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Navy
                        )
                    ) {
                        // Shows login progress
                        if (authState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Login",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Displays login errors
                    authState.errorMessage?.let {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(18.dp))

                    HorizontalDivider(color = Color(0xFFE8EDF4))

                    Spacer(Modifier.height(10.dp))

                    // Registration option
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "New to TravelMate?",
                            fontSize = 13.sp,
                            color = Gray
                        )

                        TextButton(onClick = onRegisterClick) {
                            Text(
                                "Create Account",
                                color = Orange,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}