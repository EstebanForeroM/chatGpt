package com.example.chatgpt.data

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fuel.Fuel
import fuel.delete
import fuel.get

data class MealData(
    var meals: List<SingleMealData>
)

data class SingleMealData(
    var strMeal: String
)

@OptIn(ExperimentalStdlibApi::class)
suspend fun getAIMessage(prompt: String): String {
    val response = Fuel.get("https://backendai-production-5997.up.railway.app/ai/$prompt");

    // val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    // val jsonAdapter = moshi.adapter<MealData>()
    // val mealData = jsonAdapter.fromJson(response.body.string())
    return response.body.string()


suspend fun clearAIChat() {
    Fuel.delete("https://backendai-production-5997.up.railway.app/ai")
}