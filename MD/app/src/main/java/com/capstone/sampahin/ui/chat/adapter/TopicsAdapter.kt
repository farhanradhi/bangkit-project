package com.capstone.sampahin.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sampahin.R
import com.capstone.sampahin.databinding.ItemTopicBinding

class TopicsAdapter(
    private val onItemSelected: (String) -> Unit
) : ListAdapter<String, TopicsAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: String) {
            binding.tvTopicTitle.text = translateTopic(topic, binding.root.context)
            binding.root.setOnClickListener { onItemSelected(topic) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    private fun translateTopic(label: String, context: Context): String {
        return when (label.lowercase()) {
            "besi" -> context.getString(R.string.besi)
            "daun" -> context.getString(R.string.daun)
            "kaca" -> context.getString(R.string.kaca)
            "kardus" -> context.getString(R.string.kardus)
            "kayu" -> context.getString(R.string.kayu)
            "kertas" -> context.getString(R.string.kertas)
            "plastik" -> context.getString(R.string.plastik)
            "sisa makanan" -> context.getString(R.string.sisa_makanan)
            "bukan sampah" -> context.getString(R.string.bukan_sampah)
            else -> label
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topics = getItem(position)
        holder.bind(topics)
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}