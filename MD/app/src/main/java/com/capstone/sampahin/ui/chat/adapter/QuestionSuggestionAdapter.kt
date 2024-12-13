package com.capstone.sampahin.ui.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sampahin.databinding.ItemQuestionSuggestionBinding

class QuestionSuggestionsAdapter(
    private var suggestedQuestions: List<String>,
    private val onOptionClickedCallback: OnOptionClicked
) : RecyclerView.Adapter<QuestionSuggestionsAdapter.ViewHolder>() {

    interface OnOptionClicked {
        fun onOptionClicked(optionID: Int)
    }

    inner class ViewHolder(val binding: ItemQuestionSuggestionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuestionSuggestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvQuestionSuggestion.text = suggestedQuestions[position]
        holder.itemView.setOnClickListener {
            onOptionClickedCallback.onOptionClicked(position)
        }
    }

    override fun getItemCount(): Int = suggestedQuestions.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newQuestions: List<String>) {
        suggestedQuestions = newQuestions
        notifyDataSetChanged()
    }
}