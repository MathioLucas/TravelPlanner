package com.example.travelplanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplanner.data.Destination
import com.example.travelplanner.databinding.ItemRecommendationBinding

class RecommendationsAdapter : RecyclerView.Adapter<RecommendationsAdapter.RecommendationViewHolder>() {
    private var list: List<Destination> = listOf()

    fun submitList(destinations: List<Destination>) {
        list = destinations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class RecommendationViewHolder(private val binding: ItemRecommendationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Destination) {
            binding.tvRecommendationName.text = item.name
            binding.tvRecommendationLocation.text = item.location
        }
    }
}
