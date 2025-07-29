package com.example.recipeapp.data.database

class FavoriteRepository(private val dao: FavoriteDao) {
    val allFavorites = dao.getAllFavorites()

    suspend fun insert(recipe: FavoriteRecipe) = dao.insertRecipe(recipe)
    suspend fun delete(recipe: FavoriteRecipe) = dao.deleteRecipe(recipe)

    // دالة تتحقق إذا كانت الوصفة موجودة في المفضلة
    suspend fun exists(id: String): Boolean = dao.exists(id) > 0

    // دالة تحذف بالـ recipeId
    suspend fun deleteById(id: String) = dao.deleteById(id)
}

