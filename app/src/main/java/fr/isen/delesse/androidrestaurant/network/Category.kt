package fr.isen.delesse.androidrestaurant.network

import com.google.gson.annotations.SerializedName

class Category(@SerializedName("name_fr") val name: String, val items: List<Dish>) {
    public fun getAllDishName(): List<String> {
        var dishList: List<String> = emptyList()
        this.items.forEach{
            dishList.toMutableList().add(it.name)
        }
        return dishList
    }

}