package com.example.travelmate.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SavedPlaceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // User saved places
    private fun savedPlacesCollection() =
        db.collection("users")
            .document(auth.currentUser?.uid ?: "")
            .collection("savedPlaces")

    // Save destination
    fun savePlace(
        place: Place,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = auth.currentUser

        if (user == null) {
            onResult(false, "Please login first.")
            return
        }

        val userPlace = place.copy(
            userId = user.uid
        )

        savedPlacesCollection()
            .document(place.placeId)
            .set(userPlace)
            .addOnSuccessListener {
                onResult(true, "Destination saved successfully.")
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }

    // Load saved places
    fun getSavedPlaces(
        onResult: (List<Place>) -> Unit,
        onError: (String) -> Unit
    ) {
        if (auth.currentUser == null) {
            onError("Please login first.")
            return
        }

        savedPlacesCollection()
            .get()
            .addOnSuccessListener { documents ->
                val places = documents.documents.mapNotNull {
                    it.toObject(Place::class.java)
                }

                onResult(places)
            }
            .addOnFailureListener {
                onError(it.message ?: "Unable to load saved places.")
            }
    }

    // Remove destination
    fun removePlace(
        placeId: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (auth.currentUser == null) {
            onResult(false, "Please login first.")
            return
        }

        savedPlacesCollection()
            .document(placeId)
            .delete()
            .addOnSuccessListener {
                onResult(true, "Destination removed.")
            }
            .addOnFailureListener { error ->
                onResult(false, error.message)
            }
    }
}