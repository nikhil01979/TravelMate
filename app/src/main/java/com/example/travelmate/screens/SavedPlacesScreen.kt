package com.example.travelmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelmate.viewmodel.ExploreViewModel

// Screen colors
private val SavedNavy = Color(0xFF062B68)
private val SavedOrange = Color(0xFFFF7900)

@Composable
fun SavedPlacesScreen(
    exploreViewModel: ExploreViewModel,
    onBackClick: () -> Unit,
    onPlaceSelected: () -> Unit
) {

    // Observe screen state
    val state by exploreViewModel.state.collectAsState()

    // Load saved places
    LaunchedEffect(Unit) {
        exploreViewModel.loadSavedPlaces()
    }

    // Main container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFD))
    ) {

        // Screen header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = SavedNavy
                )
            }

            Text(
                "Saved Destinations",
                fontWeight = FontWeight.Bold,
                color = SavedNavy,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(Modifier.height(12.dp))

        // Empty state
        if (state.savedPlaces.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No saved destinations yet.\nExplore and save your favourite places!",
                    color = Color.Gray
                )
            }

        } else {

            // Saved places list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = state.savedPlaces,
                    key = { it.placeId }
                ) { place ->

                    // Destination card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {

                        // Destination details
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = SavedOrange,
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(Modifier.width(12.dp))

                            // Place information
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    place.name,
                                    fontWeight = FontWeight.Bold,
                                    color = SavedNavy
                                )

                                Spacer(Modifier.height(4.dp))

                                Text(
                                    place.address,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall
                                )

                                // Map navigation
                                TextButton(
                                    onClick = {
                                        exploreViewModel.selectSavedPlace(
                                            place
                                        )
                                        onPlaceSelected()
                                    }
                                ) {
                                    Text(
                                        "View on Map",
                                        color = SavedNavy
                                    )
                                }
                            }

                            // Remove destination
                            IconButton(
                                onClick = {
                                    exploreViewModel.removeSavedPlace(
                                        place.placeId
                                    )
                                }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Remove destination",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}