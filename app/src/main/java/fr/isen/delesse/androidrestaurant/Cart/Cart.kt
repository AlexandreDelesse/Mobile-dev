package fr.isen.delesse.androidrestaurant.Cart

import fr.isen.delesse.androidrestaurant.network.Dish
import java.io.Serializable

class Cart(val item: List<CartItem>): Serializable {

}

class CartItem(val dish: Dish, val count: Int): Serializable {

}