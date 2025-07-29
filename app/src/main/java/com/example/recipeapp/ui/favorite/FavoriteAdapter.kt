package com.example.recipeapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemFavoriteBinding
import com.example.recipeapp.data.database.FavoriteRecipe

class FavoriteAdapter(
    private val onDeleteClick: (FavoriteRecipe) -> Unit,
    private val onItemClick: (FavoriteRecipe) -> Unit // 👈 إضافة كولباك جديد
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

        // تحميل البيانات في الـ views
        holder.binding.tvTitle.text = recipe.title
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .into(holder.binding.ivImage)

        // تعديل الأيقونة عشان تبقى delete باللون الأحمر
        holder.binding.btnDelete.setImageResource(R.drawable.ic_delete)
        holder.binding.btnDelete.setColorFilter(
            holder.itemView.context.getColor(android.R.color.holo_red_dark)
        )

        // Action عند الضغط على زرار الحذف
        holder.binding.btnDelete.setOnClickListener {
            onDeleteClick(recipe)
        }

        // 👈 فتح التفاصيل عند الضغط على العنصر
        holder.itemView.setOnClickListener {
            onItemClick(recipe)
        }
    }

    override fun getItemCount() = recipes.size

    fun submitList(newList: List<FavoriteRecipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}
