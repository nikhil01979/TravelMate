package com.example.travelmate.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.travelmate.R
import kotlinx.coroutines.delay

// colors
private val NavyBlue = Color(0xFF062B68)
private val Orange = Color(0xFFFF7900)
private val CyanBlue = Color(0xFF08B8F0)
private val BackgroundColor = Color(0xFFF8FAFD)
private val SecondaryText = Color(0xFF718096)

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {

    // Starts splash timer
    LaunchedEffect(Unit) {
        delay(2500)
        onSplashFinished()
    }

    // Main splash content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        BackgroundColor,
                        Color(0xFFF0F7FF)
                    )
                )
            )
    ) {

        // Main splash content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            // TravelMate logo
            Image(
                painter = painterResource(
                    id = R.drawable.logobg
                ),
                contentDescription = "TravelMate Logo",
                modifier = Modifier
                    .size(310.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            // App tagline
            Text(
                text = "Your Journey, Simplified.",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                color = NavyBlue,
                letterSpacing = 0.3.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            // App description
            Text(
                text = "Plan smarter. Travel better. Spend wisely.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = SecondaryText,
                letterSpacing = 0.2.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = Orange,
                strokeWidth = 3.dp,
                trackColor = Color(0xFFE2EAF5)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Footer Text
            Text(
                text = "PREPARING YOUR ADVENTURE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = NavyBlue.copy(alpha = 0.65f),
                letterSpacing = 2.sp
            )
        }
    }
}