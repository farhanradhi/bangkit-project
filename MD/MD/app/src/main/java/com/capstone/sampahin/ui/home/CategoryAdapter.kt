package com.capstone.sampahin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sampahin.data.Category
import com.capstone.sampahin.databinding.ItemCategoryBinding

class CategoryAdapter(private val listCategory: ArrayList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, photo) = listCategory[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategory.text = name
    }
}