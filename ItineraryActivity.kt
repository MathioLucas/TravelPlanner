package com.example.travelplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.Itinerary
import com.example.travelplanner.databinding.ActivityItineraryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItineraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItineraryBinding
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveItinerary.setOnClickListener {
            val itinerary = Itinerary(
                destination = binding.etDestination.text.toString(),
                startDate = binding.etStartDate.text.toString(),
                endDate = binding.etEndDate.text.toString(),
                activities = binding.etActivities.text.toString()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val id = db.appDao().insertItinerary(itinerary)
                runOnUiThread {
                    if (id > 0) {
                        binding.tvStatus.text = "Itinerary saved successfully!"
                    } else {
                        binding.tvStatus.text = "Error saving itinerary"
                    }
                }
            }
        }
    }
}
