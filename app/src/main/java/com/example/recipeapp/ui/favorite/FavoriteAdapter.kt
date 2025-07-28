package com.example.recipeapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.ItemFavoriteBinding
import com.example.recipeapp.data.database.FavoriteRecipe

class FavoriteAdapter(
    private val onDeleteClick: (FavoriteRecipe) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    private var recipes = listOf<FavoriteRecipe>()

    inner class FavViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.tvTitle.text = recipe.title
        Glide.with(holder.itemView.context).load(recipe.imageUrl).into(holder.binding.ivImage)

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(recipe)
        }
    }

    override fun getItemCount() = recipes.size

    fun submitList(newList: List<FavoriteRecipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}
