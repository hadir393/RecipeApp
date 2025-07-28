package com.example.recipeapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.data.api.ApiService
import com.example.recipeapp.databinding.FragmentSearchBinding
import com.example.recipeapp.ui.detail.RecipeDetailFragment
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // نجهز الـ Adapter مع الضغط على العنصر
        adapter = SearchAdapter { recipe ->
            // نفتح RecipeDetailFragment ونبعت البيانات
            val bundle = Bundle().apply {
                putString("idMeal", recipe.idMeal)
                putString("strMeal", recipe.strMeal)
                putString("strMealThumb", recipe.strMealThumb)
                putString("strInstructions", "Instructions for ${recipe.strMeal}") // مؤقت
            }
            val detailFragment = RecipeDetailFragment()
            detailFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSearch.adapter = adapter

        // Search Button
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                searchRecipes(query)
            } else {
                binding.etSearch.error = "Please enter a search term"
            }
        }

        return binding.root
    }

    // هنا هنعمل البحث تلقائي لو فيه كلمة مكتوبة لما نرجع للشاشة دي
    override fun onResume() {
        super.onResume()
        val query = binding.etSearch.text.toString()
        if (query.isNotEmpty()) {
            searchRecipes(query)
        }
    }

    private fun searchRecipes(query: String) {
        val api = ApiService.getInstance()

        lifecycleScope.launch {
            try {
                val response = api.searchMeals(query)
                val recipes = response.meals ?: emptyList()
                adapter.submitList(recipes)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
