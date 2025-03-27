package com.example.travelplanner.data

import androidx.room.*

@Dao
interface AppDao {

    // --- User queries ---
    @Insert
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    suspend fun getUserById(uid: Int): User?

    // --- Itinerary queries ---
    @Insert
    suspend fun insertItinerary(itinerary: Itinerary): Long

    @Query("SELECT * FROM itineraries")
    suspend fun getAllItineraries(): List<Itinerary>

    // --- Destination queries ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestinations(destinations: List<Destination>)

    @Query("SELECT * FROM destinations WHERE name LIKE :query")
    suspend fun searchDestinations(query: String): List<Destination>

    @Query("SELECT * FROM destinations")
    suspend fun getAllDestinations(): List<Destination>
}
