package com.example.recipeapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ItemSearchBinding
import com.example.recipeapp.data.model.Recipe

class SearchAdapter(
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var recipes = listOf<Recipe>()

    inner class SearchViewHolder(val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.tvTitle.text = recipe.strMeal
        Glide.with(holder.itemView.context).load(recipe.strMealThumb).into(holder.binding.ivImage)

        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    override fun getItemCount() = recipes.size

    fun submitList(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}
