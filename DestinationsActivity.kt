package com.example.travelplanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplanner.data.AppDatabase
import com.example.travelplanner.data.Destination
import com.example.travelplanner.databinding.ActivityDestinationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class DestinationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDestinationsBinding
    private lateinit var adapter: DestinationsAdapter
    private val db by lazy { AppDatabase.getDatabase(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DestinationsAdapter()
        binding.recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val cached = db.appDao().getAllDestinations()
            if (cached.isEmpty()) {
                val destinations = loadDestinationsFromAssets()
                db.appDao().insertDestinations(destinations)
                adapter.submitList(destinations)
            } else {
                adapter.submitList(cached)
            }
        }

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val results = db.appDao().searchDestinations("%$query%")
                runOnUiThread {
                    adapter.submitList(results)
                }
            }
        }
    }

    private fun loadDestinationsFromAssets(): List<Destination> {
        val list = mutableListOf<Destination>()
        val jsonString = assets.open("destinations.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                Destination(
                    id = obj.getString("id"),
                    name = obj.getString("name"),
                    location = obj.getString("location"),
                    weather = obj.getString("weather"),
                    attractions = obj.getString("attractions")
                )
            )
        }
        return list
    }
}
