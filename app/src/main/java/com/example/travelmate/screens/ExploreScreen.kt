package com.example.travelmate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelmate.viewmodel.ExploreViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// Screen colors
private val ExploreNavy = Color(0xFF062B68)
private val ExploreOrange = Color(0xFFFF7900)
private val ExploreGray = Color(0xFF718096)

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel,
    onBackClick: () -> Unit,
    onSavedPlacesClick: () -> Unit
) {

    // Observe screen state
    val state by exploreViewModel.state.collectAsState()

    // Default map location
    val defaultLocation = LatLng(20.5937, 78.9629)

    // Selected map location
    val selectedLocation = state.selectedPlace?.let {
        LatLng(it.latitude, it.longitude)
    }

    // Initialize camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            defaultLocation,
            4f
        )
    }

    // Moves map when destination changes
    LaunchedEffect(selectedLocation) {
        selectedLocation?.let {
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    // Main screen container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFD))
    ) {

        // Header section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Back navigation
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = ExploreNavy
                )
            }

            // Header title
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Explore Destinations",
                    fontWeight = FontWeight.Bold,
                    color = ExploreNavy,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    "Discover places for your next trip",
                    color = ExploreGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Saved places navigation
            IconButton(onClick = onSavedPlacesClick) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Saved destinations",
                    tint = ExploreOrange
                )
            }
        }

        // Destination search
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                exploreViewModel.updateSearchQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            placeholder = {
                Text("Search destinations...")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = ExploreNavy
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ExploreNavy,
                unfocusedBorderColor = Color(0xFFDCE3ED),
                cursorColor = ExploreOrange
            )
        )

        // Search loading indicator
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = ExploreOrange
            )
        }

        // Display search suggestions
        if (state.suggestions.isNotEmpty()) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        Color.White,
                        RoundedCornerShape(12.dp)
                    )
            ) {

                items(state.suggestions) { suggestion ->

                    // Suggestion item
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                exploreViewModel.selectPlace(
                                    suggestion.placeId
                                )
                            }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = ExploreOrange
                        )

                        Spacer(Modifier.width(10.dp))

                        Text(
                            suggestion.description,
                            color = ExploreNavy
                        )
                    }

                    // Item separator
                    HorizontalDivider(
                        color = Color(0xFFEDF0F5)
                    )
                }
            }
        }

        // Display error message
        state.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Google Maps display
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            cameraPositionState = cameraPositionState
        ) {

            // Selected place marker
            state.selectedPlace?.let { place ->

                Marker(
                    state = MarkerState(
                        position = LatLng(
                            place.latitude,
                            place.longitude
                        )
                    ),
                    title = place.name,
                    snippet = place.address
                )
            }
        }

        // Selected destination details
        state.selectedPlace?.let { place ->

            // Destination information card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {

                // Destination information
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        place.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = ExploreNavy
                    )

                    Spacer(Modifier.height(8.dp))

                    // Destination address
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = ExploreOrange
                        )

                        Spacer(Modifier.width(5.dp))

                        Text(
                            place.address,
                            color = ExploreGray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    // Save destination button
                    Button(
                        onClick = {
                            exploreViewModel.saveSelectedPlace()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ExploreNavy
                        )
                    ) {

                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = null
                        )

                        Spacer(Modifier.width(8.dp))

                        Text("Save Destination")
                    }
                }
            }
        }

        // Display success message
        state.message?.let { message ->

            Text(
                text = message,
                color = Color(0xFF16803C),
                modifier = Modifier.padding(12.dp)
            )

            // Clear message automatically
            LaunchedEffect(message) {
                kotlinx.coroutines.delay(2500)
                exploreViewModel.clearMessage()
            }
        }
    }
}