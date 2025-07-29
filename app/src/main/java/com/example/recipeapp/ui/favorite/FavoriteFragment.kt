package com.example.recipeapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.FavoriteViewModel
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoriteBinding
import com.example.recipeapp.ui.detail.RecipeDetailFragment

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // تمرير كولباكين: الحذف وفتح التفاصيل
        adapter = FavoriteAdapter(
            onDeleteClick = { recipe ->
                viewModel.deleteById(recipe.recipeId)
            },
            onItemClick = { recipe ->
                // فتح صفحة التفاصيل عند الضغط على العنصر
                val bundle = Bundle().apply {
                    putString("idMeal", recipe.recipeId)
                    putString("strMeal", recipe.title)
                    putString("strMealThumb", recipe.imageUrl)
                    putString("strInstructions", "No instructions available")
                }
                val detailFragment = RecipeDetailFragment()
                detailFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFavorites.adapter = adapter

        viewModel.allFavorites.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        return binding.root
    }
}
