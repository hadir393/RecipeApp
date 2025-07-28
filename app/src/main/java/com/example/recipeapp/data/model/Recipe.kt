package com.example.recipeapp.data.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String
)
