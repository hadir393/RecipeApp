package com.example.recipeapp.data.api

import com.example.recipeapp.data.model.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class MealsResponse(val meals: List<Recipe>?)

interface ApiService {
    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealsResponse

    companion object {
        fun getInstance(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
