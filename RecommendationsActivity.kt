package com.example.travelplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.Destination
import com.example.travelplanner.databinding.ActivityRecommendationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationsBinding
    private lateinit var adapter: RecommendationsAdapter
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewRecommendations.layoutManager = LinearLayoutManager(this)
        adapter = RecommendationsAdapter()
        binding.recyclerViewRecommendations.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val all = db.appDao().getAllDestinations()
            val recommendations = all.filter { it.attractions.contains("Adventure", ignoreCase = true) }
                .ifEmpty { all } // fallback if none match
            runOnUiThread { adapter.submitList(recommendations) }
        }
    }
}
