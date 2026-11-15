package com.example.travelmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmate.viewmodel.AuthViewModel

// colors
private val DashNavy = Color(0xFF062B68)
private val DashOrange = Color(0xFFFF7900)
private val DashGray = Color(0xFF718096)

@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    // Dashboard background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFFF0F7FF))
                )
            )
    ) {
        // Main dashboard content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // Header section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "TravelMate",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DashNavy
                )

                // Logout action
                TextButton(onClick = {
                    authViewModel.logout()
                    onLogout()
                }) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = null,
                        tint = DashOrange
                    )
                    Spacer(Modifier.width(5.dp))
                    Text("Logout", color = DashOrange)
                }
            }

            Spacer(Modifier.height(32.dp))

            // Welcome message
            Text(
                "Welcome to TravelMate!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DashNavy
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Plan your journeys and manage your travel expenses with ease.",
                fontSize = 14.sp,
                color = DashGray,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(28.dp))

            // Dashboard heading
            Text(
                "Explore Your Dashboard",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DashNavy
            )

            Spacer(Modifier.height(16.dp))

            // Travel planning card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Icon(
                        Icons.Default.FlightTakeoff,
                        contentDescription = null,
                        tint = DashOrange,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Travel Planning",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Plan your trips and organise your travel details in one place.",
                        fontSize = 14.sp,
                        color = DashGray,
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    // Feature availability
                    Text(
                        "Coming Soon",
                        color = DashOrange,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Expense management card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Icon(
                        Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = DashNavy,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "Expense Management",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        "Keep track of your travel expenses and manage your budget easily.",
                        fontSize = 14.sp,
                        color = DashGray,
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    // Feature availability
                    Text(
                        "Coming Soon",
                        color = DashOrange,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}