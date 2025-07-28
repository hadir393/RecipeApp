package com.example.recipeapp.data.database

class FavoriteRepository(private val dao: FavoriteDao) {
    val allFavorites = dao.getAllFavorites()

    suspend fun insert(recipe: FavoriteRecipe) = dao.insertRecipe(recipe)
    suspend fun delete(recipe: FavoriteRecipe) = dao.deleteRecipe(recipe)
}
