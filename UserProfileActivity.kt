package com.example.travelplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.databinding.ActivityUserProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getIntExtra("userId", -1)
        if(userId != -1){
            loadUserProfile()
        }

        binding.btnDestinations.setOnClickListener {
            startActivity(Intent(this, DestinationsActivity::class.java))
        }
        binding.btnItinerary.setOnClickListener {
            startActivity(Intent(this, ItineraryActivity::class.java))
        }
        binding.btnRecommendations.setOnClickListener {
            startActivity(Intent(this, RecommendationsActivity::class.java))
        }
    }

    private fun loadUserProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.appDao().getUserById(userId)
            runOnUiThread {
                binding.tvUserName.text = user?.name ?: "No Name"
                binding.tvFavoriteActivities.text = user?.favoriteActivities ?: "Not specified"
            }
        }
    }
}
