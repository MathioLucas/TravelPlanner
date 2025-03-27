package com.example.travelplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itineraries")
data class Itinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val activities: String // Comma-separated activities
)
