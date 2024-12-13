package com.capstone.sampahin.ui.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sampahin.data.maps.MapsResponsesItem
import com.capstone.sampahin.databinding.PlaceLayoutBinding

class MapsAdapter : ListAdapter<MapsResponsesItem, MapsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlaceLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }

    class ViewHolder(private val binding: PlaceLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: MapsResponsesItem) {
            with(binding) {
                tvTitle.text = place.name
                tvAddress.text = place.address
            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MapsResponsesItem>() {
            override fun areItemsTheSame(
                oldItem: MapsResponsesItem,
                newItem: MapsResponsesItem
            ): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: MapsResponsesItem,
                newItem: MapsResponsesItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    
}