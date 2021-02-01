package fr.isen.delesse.androidrestaurant.Cart

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.network.Dish
import java.io.File
import java.io.Serializable

class Cart(val items: MutableList<CartItem>): Serializable {
    //val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
    var itemCount : Int = 0
    get() {
        return items
                .map { it.count }
                .reduce { acc, i -> acc + i }
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
        const val CART_FILE = "cart.json"
    }
}

class CartItem(val dish: Dish, var count: Int): Serializable {

}