package fr.isen.delesse.androidrestaurant.order

import org.json.JSONObject
import java.io.Serializable

class Order(val orderItems: MutableList<OrderItem>): Serializable {


}

class OrderItem(val item: JSONObject): Serializable{

}