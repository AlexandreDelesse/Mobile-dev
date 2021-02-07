package fr.isen.delesse.androidrestaurant.cart

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.network.Dish
import java.io.File
import java.io.Serializable

class Cart(val items: MutableList<CartItem>): Serializable {
    //val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
    var itemCount : Int = 0
    get() {
        return if(items.count() > 0){
            items
                .map { it.count }
                .reduce { acc, i -> acc + i }
        } else {
            0
        }
    }

    fun addItem(item: CartItem) {
        val existingItem = items.firstOrNull {
            it.dish.name == item.dish.name
        }
        existingItem?.let {
            existingItem.count += item.count
        }?: run {
            items.add(item)
        }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
        updateCount(context)
    }

    fun updateCount(context: Context){
        val count = itemCount
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, count)
        editor.apply()
    }

    fun getTotal(): Float{
        var total = 0F
        items.forEach {
            total += it.count.toFloat() * it.dish.prices.first().price.toFloat()
        }
        return total
    }


    companion object {
        fun getCart(context: Context): Cart {
            val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
            return if(jsonFile.exists()){
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Cart::class.java)
            } else {
                Cart(mutableListOf())
            }
        }

        fun deleteCart(context: Context){
            val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
            jsonFile.delete()
            val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.remove(ITEMS_COUNT)
            editor.apply()
        }


        const val CART_FILE = "cart.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}

class CartItem(val dish: Dish, var count: Int): Serializable {

}