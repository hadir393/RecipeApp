package com.example.recipeapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.recipeapp.FavoriteViewModel
import com.example.recipeapp.R
import com.example.recipeapp.data.database.FavoriteRecipe
import com.example.recipeapp.databinding.FragmentRecipeDetailBinding
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: FavoriteViewModel by viewModels()

    private var recipeId: String? = null
    private var title: String? = null
    private var imageUrl: String? = null
    private var instructions: String? = null

    private var isFavorite = false   // حالة القلب

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // استلام البيانات من الـ Bundle
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

        // إنشاء الكائن لمرة واحدة
        val recipe = FavoriteRecipe(
            recipeId = recipeId ?: "",
            title = title ?: "",
            imageUrl = imageUrl ?: ""
        )

        // تحقق إذا كانت موجودة في المفضلة بالفعل
        lifecycleScope.launch {
            val exists = viewModel.exists(recipe.recipeId)
            isFavorite = exists
            binding.btnFavorite.setImageResource(
                if (exists) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }

        // الضغط على القلب (إضافة / حذف)
        binding.btnFavorite.setOnClickListener {
            lifecycleScope.launch {
                if (!isFavorite) {
                    viewModel.insert(recipe)
                    Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show()
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                    isFavorite = true
                } else {
                    // حذف باستخدام الكائن مباشرة
                    viewModel.deleteById(recipe.recipeId)
                    Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    isFavorite = false
                }
            }
        }

        // زرار الرجوع
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }
}
