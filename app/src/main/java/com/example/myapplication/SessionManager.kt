package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

data class FoodItem(
    val id: String,
    val name: String,
    val details: String,
    val colorValue: Int
)

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    fun saveUser(nombre: String, correo: String, password: String) {
        prefs.edit().apply {
            putString("nombre", nombre)
            putString("correo", correo)
            putString("password", password)
            apply()
        }
    }

    fun login(correo: String, password: String): Boolean {
        val savedCorreo = prefs.getString("correo", "")
        val savedPassword = prefs.getString("password", "")
        if (correo == savedCorreo && password == savedPassword) {
            prefs.edit().putBoolean("isLoggedIn", true).apply()
            return true
        }
        return false
    }

    fun logout() {
        prefs.edit().putBoolean("isLoggedIn", false).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)

    fun getNombre(): String = prefs.getString("nombre", "Alumno") ?: "Alumno"
    fun getCorreo(): String = prefs.getString("correo", "") ?: ""
    
    fun saveImage(uri: String) {
        prefs.edit().putString("profile_image", uri).apply()
    }
    
    fun getImage(): String? = prefs.getString("profile_image", null)

    // Food Items management using JSON string
    fun getFoodItems(): List<FoodItem> {
        val jsonString = prefs.getString("food_items", "[]") ?: "[]"
        val jsonArray = JSONArray(jsonString)
        val list = mutableListOf<FoodItem>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                FoodItem(
                    obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("details"),
                    obj.getInt("color")
                )
            )
        }
        return list
    }

    fun saveFoodItem(item: FoodItem) {
        val items = getFoodItems().toMutableList()
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            items[index] = item
        } else {
            items.add(item)
        }
        persistFoodItems(items)
    }

    fun deleteFoodItem(id: String) {
        val items = getFoodItems().filter { it.id != id }
        persistFoodItems(items)
    }
    
    fun deleteMultipleFoodItems(ids: Set<String>) {
        val items = getFoodItems().filter { it.id !in ids }
        persistFoodItems(items)
    }

    private fun persistFoodItems(items: List<FoodItem>) {
        val jsonArray = JSONArray()
        items.forEach {
            val obj = JSONObject()
            obj.put("id", it.id)
            obj.put("name", it.name)
            obj.put("details", it.details)
            obj.put("color", it.colorValue)
            jsonArray.put(obj)
        }
        prefs.edit().putString("food_items", jsonArray.toString()).apply()
    }
}
