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
private val SignupNavy = Color(0xFF062B68)
private val SignupOrange = Color(0xFFFF7900)
private val SignupGray = Color(0xFF718096)

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onRegistrationSuccess: () -> Unit,
    onLoginClick: () -> Unit
) {
    // Observes auth state
    val authState by authViewModel.authState.collectAsState()

    // Stores signup details
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    // Checks password match
    val passwordsMatch = password == confirmPassword

    // Handles registration success
    LaunchedEffect(authState.isAuthenticated) {
        if (authState.isAuthenticated) onRegistrationSuccess()
    }

    // Signup background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFFF0F7FF))
                )
            )
    ) {
        // Screen layout
        BoxWithConstraints(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = maxHeight)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Main heading
                Text(
                    "Create Account",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = SignupNavy
                )

                Spacer(Modifier.height(6.dp))

                // Welcome message
                Text(
                    "Start your journey with TravelMate.",
                    fontSize = 14.sp,
                    color = SignupGray
                )

                Spacer(Modifier.height(30.dp))

                // Signup form
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        "Sign Up",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = SignupNavy
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        "Enter your details to create an account",
                        fontSize = 13.sp,
                        color = SignupGray
                    )

                    Spacer(Modifier.height(20.dp))

                    // Email label
                    Text(
                        "Email Address",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SignupNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Email input
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your email") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, null, tint = SignupNavy)
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SignupNavy,
                            unfocusedBorderColor = Color(0xFFDCE3ED),
                            cursorColor = SignupOrange
                        )
                    )

                    Spacer(Modifier.height(14.dp))

                    // Password label
                    Text(
                        "Password",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SignupNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Password input
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Create a password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null, tint = SignupNavy)
                        },
                        // Password visibility
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = "Toggle password visibility",
                                    tint = SignupGray
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
                            focusedBorderColor = SignupNavy,
                            unfocusedBorderColor = Color(0xFFDCE3ED),
                            cursorColor = SignupOrange
                        )
                    )

                    Spacer(Modifier.height(14.dp))

                    // Confirmation label
                    Text(
                        "Confirm Password",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SignupNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Confirmation input
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Confirm your password") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, null, tint = SignupNavy)
                        },
                        // Confirmation visibility
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmVisible = !confirmVisible
                            }) {
                                Icon(
                                    if (confirmVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = "Toggle confirm password visibility",
                                    tint = SignupGray
                                )
                            }
                        },
                        visualTransformation = if (confirmVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SignupNavy,
                            unfocusedBorderColor = Color(0xFFDCE3ED),
                            cursorColor = SignupOrange
                        )
                    )

                    // Password validation
                    if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Passwords do not match.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    // Registration button
                    Button(
                        onClick = {
                            if (passwordsMatch) {
                                authViewModel.register(
                                    email = email.trim(),
                                    password = password
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !authState.isLoading &&
                                email.isNotBlank() &&
                                password.isNotBlank() &&
                                confirmPassword.isNotBlank() &&
                                passwordsMatch,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SignupNavy
                        )
                    ) {
                        // Registration progress
                        if (authState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Registration errors
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

                    // Login navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Already have an account?",
                            fontSize = 13.sp,
                            color = SignupGray
                        )

                        TextButton(onClick = onLoginClick) {
                            Text(
                                "Login",
                                color = SignupOrange,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}