package com.example.recipeapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Query("SELECT COUNT(*) FROM favorite_recipes WHERE recipeId = :id")
    suspend fun exists(id: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: FavoriteRecipe)

    @Delete
    suspend fun deleteRecipe(recipe: FavoriteRecipe)

    // الميثود الجديدة
    @Query("DELETE FROM favorite_recipes WHERE recipeId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM favorite_recipes")
    fun getAllFavorites(): LiveData<List<FavoriteRecipe>>
}

