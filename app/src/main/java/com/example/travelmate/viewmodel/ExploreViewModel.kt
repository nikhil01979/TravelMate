package com.example.travelmate.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.travelmate.BuildConfig
import com.example.travelmate.data.Place
import com.example.travelmate.data.SavedPlaceRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place as GooglePlace
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Suggestion model
data class PlaceSuggestion(
    val placeId: String,
    val description: String
)

// Explore screen state
data class ExploreState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val suggestions: List<PlaceSuggestion> = emptyList(),
    val selectedPlace: Place? = null,
    val savedPlaces: List<Place> = emptyList(),
    val errorMessage: String? = null,
    val message: String? = null
)

// Explore ViewModel
class ExploreViewModel(
    application: Application
) : AndroidViewModel(application) {

    // Repository instance
    private val repository = SavedPlaceRepository()

    // Firebase authentication
    private val auth = FirebaseAuth.getInstance()

    // Request tracking
    private var searchRequestId = 0

    // Places client
    private val placesClient: PlacesClient by lazy {
        val apiKey = BuildConfig.MAPS_API_KEY

        // Validate API key
        require(apiKey.isNotBlank()) {
            "Google Maps API key is missing. Check local.properties."
        }

        // Initialize Places
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(
                getApplication<Application>(),
                apiKey
            )
        }

        Places.createClient(
            getApplication<Application>()
        )
    }

    // Internal state
    private val _state = MutableStateFlow(
        ExploreState()
    )

    // Public state
    val state: StateFlow<ExploreState> = _state.asStateFlow()

    // Update search query
    fun updateSearchQuery(query: String) {

        searchRequestId++

        _state.value = _state.value.copy(
            searchQuery = query,
            errorMessage = null,
            message = null
        )

        if (query.isBlank()) {

            _state.value = _state.value.copy(
                isLoading = false,
                suggestions = emptyList()
            )

            return
        }

        searchPlaces(
            query = query,
            requestId = searchRequestId
        )
    }

    // Search Google Places
    private fun searchPlaces(
        query: String,
        requestId: Int
    ) {

        if (query.isBlank()) return

        _state.value = _state.value.copy(
            isLoading = true,
            errorMessage = null
        )

        try {

            val request =
                FindAutocompletePredictionsRequest.builder()
                    .setQuery(query)
                    .build()

            placesClient.findAutocompletePredictions(request)

                .addOnSuccessListener { response ->

                    // Ignore outdated responses
                    if (requestId != searchRequestId) {
                        return@addOnSuccessListener
                    }

                    val suggestions =
                        response.autocompletePredictions.map {
                            PlaceSuggestion(
                                placeId = it.placeId,
                                description = it.getFullText(null).toString()
                            )
                        }

                    _state.value = _state.value.copy(
                        isLoading = false,
                        suggestions = suggestions,
                        errorMessage = if (suggestions.isEmpty()) {
                            "No destinations found."
                        } else {
                            null
                        }
                    )
                }

                .addOnFailureListener { error ->

                    // Ignore outdated responses
                    if (requestId != searchRequestId) {
                        return@addOnFailureListener
                    }

                    _state.value = _state.value.copy(
                        isLoading = false,
                        suggestions = emptyList(),
                        errorMessage = error.message
                            ?: "Destination search failed."
                    )
                }

        } catch (exception: Exception) {

            // Handle search errors
            if (requestId == searchRequestId) {

                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = exception.message
                        ?: "Unable to search destinations."
                )
            }
        }
    }

    // Fetch destination details
    fun selectPlace(placeId: String) {
        searchRequestId++
        _state.value = _state.value.copy(
            isLoading = true,
            suggestions = emptyList(),
            errorMessage = null
        )
        try {
            // Requested fields
            val fields = listOf(
                GooglePlace.Field.ID,
                GooglePlace.Field.NAME,
                GooglePlace.Field.ADDRESS,
                GooglePlace.Field.LAT_LNG
            )
            val request = FetchPlaceRequest.newInstance(
                placeId,
                fields
            )

            placesClient.fetchPlace(request)
                .addOnSuccessListener { response ->

                    // Extract place details
                    val googlePlace = response.place
                    val location = googlePlace.latLng

                    // Validate coordinates
                    if (location == null) {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = "Location details unavailable."
                        )
                        return@addOnSuccessListener
                    }

                    // Create place model
                    val place = Place(
                        placeId = googlePlace.id ?: placeId,
                        name = googlePlace.name ?: "Unknown place",
                        address = googlePlace.address
                            ?: "Address unavailable",
                        latitude = location.latitude,
                        longitude = location.longitude,
                        userId = auth.currentUser?.uid ?: ""
                    )

                    _state.value = _state.value.copy(
                        isLoading = false,
                        selectedPlace = place,
                        searchQuery = place.name,
                        suggestions = emptyList(),
                        errorMessage = null
                    )
                }

                .addOnFailureListener { error ->

                    // Handle fetch errors
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = error.message
                            ?: "Unable to load destination."
                    )
                }

        } catch (exception: Exception) {

            // Handle request errors
            _state.value = _state.value.copy(
                isLoading = false,
                errorMessage = exception.message
                    ?: "Unable to load destination."
            )
        }
    }

    // Save selected place
    fun saveSelectedPlace() {

        val place = _state.value.selectedPlace ?: return

        // Check authentication
        if (auth.currentUser == null) {

            _state.value = _state.value.copy(
                errorMessage = "Please login to save destinations."
            )

            return
        }

        // Save to Firebase
        repository.savePlace(place) { success, message ->

            _state.value = _state.value.copy(
                message = if (success) message else null,
                errorMessage = if (success) null else message
            )

            if (success) {
                loadSavedPlaces()
            }
        }
    }

    // Load saved places
    fun loadSavedPlaces() {

        repository.getSavedPlaces(

            onResult = { places ->

                _state.value = _state.value.copy(
                    savedPlaces = places,
                    errorMessage = null
                )
            },

            onError = { error ->

                _state.value = _state.value.copy(
                    errorMessage = error
                )
            }
        )
    }

    // Remove saved place
    fun removeSavedPlace(placeId: String) {

        // Delete from Firebase
        repository.removePlace(placeId) { success, message ->

            _state.value = _state.value.copy(
                message = if (success) message else null,
                errorMessage = if (success) null else message
            )

            if (success) {
                loadSavedPlaces()
            }
        }
    }

    // Select saved place
    fun selectSavedPlace(place: Place) {

        _state.value = _state.value.copy(
            selectedPlace = place,
            searchQuery = place.name,
            suggestions = emptyList(),
            errorMessage = null
        )
    }

    // Clear notifications
    fun clearMessage() {

        _state.value = _state.value.copy(
            message = null,
            errorMessage = null
        )
    }
}