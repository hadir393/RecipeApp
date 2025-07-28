package com.example.recipeapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.recipeapp.FavoriteViewModel
import com.example.recipeapp.data.database.FavoriteRecipe
import com.example.recipeapp.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: FavoriteViewModel by viewModels()

    private var recipeId: String? = null
    private var title: String? = null
    private var imageUrl: String? = null
    private var instructions: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // استلام البيانات
        arguments?.let {
            recipeId = it.getString("idMeal")
            title = it.getString("strMeal")
            imageUrl = it.getString("strMealThumb")
            instructions = it.getString("strInstructions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        // عرض البيانات
        binding.tvTitle.text = title
        binding.tvInstructions.text = instructions ?: "No instructions available"
        Glide.with(requireContext()).load(imageUrl).into(binding.ivRecipe)

        // زرار إضافة للمفضلة
        binding.btnFavorite.setOnClickListener {
            val recipe = FavoriteRecipe(
                recipeId = recipeId ?: "",
                title = title ?: "",
                imageUrl = imageUrl ?: ""
            )
            viewModel.insert(recipe)
            Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show()
        }

        // زرار الرجوع
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }
}
