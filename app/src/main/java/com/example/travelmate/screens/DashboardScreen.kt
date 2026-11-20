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

// Dashboard colors
private val DashNavy = Color(0xFF062B68)
private val DashOrange = Color(0xFFFF7900)
private val DashGray = Color(0xFF718096)

@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit,
    onExploreClick: () -> Unit,
    onSavedPlacesClick: () -> Unit
) {

    // Background container
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color(0xFFF0F7FF)
                    )
                )
            )
    ) {

        // Scrollable dashboard content
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

                // Application title
                Text(
                    "TravelMate",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = DashNavy
                )

                // Logout action
                TextButton(
                    onClick = {
                        authViewModel.logout()
                        onLogout()
                    }
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = null,
                        tint = DashOrange
                    )

                    Spacer(Modifier.width(5.dp))

                    Text(
                        "Logout",
                        color = DashOrange
                    )
                }
            }

            Spacer(Modifier.height(30.dp))

            // Welcome heading
            Text(
                "Welcome to TravelMate!",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = DashNavy
            )

            Spacer(Modifier.height(8.dp))

            // Welcome description
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
                onClick = onExploreClick,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {

                // Card content
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    // Travel icon
                    Icon(
                        Icons.Default.FlightTakeoff,
                        contentDescription = null,
                        tint = DashOrange,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    // Card title
                    Text(
                        "Explore Destinations",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Card description
                    Text(
                        "Search destinations, explore Google Maps and discover interesting places.",
                        fontSize = 14.sp,
                        color = DashGray,
                        lineHeight = 20.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    // Navigation label
                    Text(
                        "Explore Now →",
                        color = DashOrange,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Saved places card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = onSavedPlacesClick,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {

                // Card content
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    // Favorite icon
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = DashOrange,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    // Card title
                    Text(
                        "Saved Destinations",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Card description
                    Text(
                        "View and manage your favourite travel destinations.",
                        fontSize = 14.sp,
                        color = DashGray
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

                // Card content
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    // Wallet icon
                    Icon(
                        Icons.Default.AccountBalanceWallet,
                        contentDescription = null,
                        tint = DashNavy,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    // Card title
                    Text(
                        "Expense Management",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = DashNavy
                    )

                    Spacer(Modifier.height(6.dp))

                    // Card description
                    Text(
                        "Keep track of your travel expenses and manage your budget easily.",
                        fontSize = 14.sp,
                        color = DashGray
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