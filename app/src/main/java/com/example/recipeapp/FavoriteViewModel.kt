package com.example.recipeapp

import android.app.Application
import androidx.lifecycle.*
import com.example.recipeapp.data.database.FavoriteRepository
import kotlinx.coroutines.launch
import com.example.recipeapp.data.database.FavoriteDatabase
import com.example.recipeapp.data.database.FavoriteRecipe

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteRepository
    val allFavorites: LiveData<List<FavoriteRecipe>>

    init {
        val dao = FavoriteDatabase.getDatabase(application).favoriteDao()
        repository = FavoriteRepository(dao)
        allFavorites = repository.allFavorites
    }

    fun insert(recipe: FavoriteRecipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

//    fun delete(recipe: FavoriteRecipe) = viewModelScope.launch {
//        repository.delete(recipe)
//    }

    // دالة التحقق من وجود الوصفة في المفضلة
    suspend fun exists(id: String): Boolean {
        return repository.exists(id)
    }
    fun deleteById(id: String) = viewModelScope.launch {
        repository.deleteById(id)
    }

}
