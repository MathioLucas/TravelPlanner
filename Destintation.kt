package com.example.travelplanner.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class Destination(
    @PrimaryKey val id: String,
    val name: String,
    val location: String,
    val weather: String,
    val attractions: String // Comma-separated list
)
