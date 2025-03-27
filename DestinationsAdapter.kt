package com.example.travelplanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplanner.data.Destination
import com.example.travelplanner.databinding.ItemDestinationBinding

class DestinationsAdapter : RecyclerView.Adapter<DestinationsAdapter.DestinationViewHolder>() {
    private var list: List<Destination> = listOf()

    fun submitList(destinations: List<Destination>) {
        list = destinations
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class DestinationViewHolder(private val binding: ItemDestinationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Destination) {
            binding.tvDestinationName.text = item.name
            binding.tvLocation.text = item.location
        }
    }
}
