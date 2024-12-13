package com.capstone.sampahin.ui.profilemenu.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sampahin.R
import com.capstone.sampahin.data.history.HistoryItem
import com.capstone.sampahin.databinding.HistoryLayoutBinding

class HistoryAdapter : ListAdapter<HistoryItem, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: HistoryLayoutBinding, private val context: Context)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: HistoryItem) {

            val translatedLabel = translateLabel(history.predictedClass ?: "", context)
            val confidenceValue = (history.confidence as? Double)?.times(100) ?: 0.0
            val result = String.format(
                context.getString(R.string.result_label),
                translatedLabel,
                confidenceValue
            )

            binding.apply {
                tvTitle.text = result
                tvDate.text = history.timestamp
                Glide.with(binding.root.context)
                    .load(history.imageUrl)
                    .centerCrop()
                    .into(historyImage)
            }
        }

        private fun translateLabel(label: String, context: Context): String {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }
    

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}