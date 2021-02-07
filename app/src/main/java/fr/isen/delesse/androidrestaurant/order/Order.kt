package fr.isen.delesse.androidrestaurant.order

import fr.isen.delesse.androidrestaurant.cart.CartItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class Order(val data: MutableList<OrderMessage> ): Serializable {

}

class OrderMessage(val message: String): Serializable {

}
