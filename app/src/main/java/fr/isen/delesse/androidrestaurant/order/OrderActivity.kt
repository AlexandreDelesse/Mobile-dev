package fr.isen.delesse.androidrestaurant.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.cart.Cart
import fr.isen.delesse.androidrestaurant.cart.CartActivity
import fr.isen.delesse.androidrestaurant.cart.CartItem
import fr.isen.delesse.androidrestaurant.databinding.ActivityOrderBinding
import fr.isen.delesse.androidrestaurant.databinding.OrderListItemCellBinding
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import fr.isen.delesse.androidrestaurant.user.User
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
        val responseFromCartActivity = intent.getSerializableExtra(ORDER_LIST) as? String
        var orderList: MutableList<Cart> = mutableListOf()
        responseFromCartActivity?.let {
            orderList = parseResponse(responseFromCartActivity)
            binding.orderRecyclerView.adapter = OrderListAdapter(orderList, this)
        }?: run {
            makeRequest()
        }




    }

    fun parseResponse(response: String): MutableList<Cart>{
        var parsedResponse = GsonBuilder().create().fromJson(response, Order::class.java)
        val orderList = mutableListOf<Cart>()
        parsedResponse.data.forEach {
            orderList.add(GsonBuilder().create().fromJson(it.message, Cart::class.java))
        }
        return orderList
    }

    private fun makeRequest() {

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_LIST_ORDER
        val jsonBody = JSONObject()
        jsonBody.put("id_shop", 1)
        jsonBody.put("id_user", User.getUserId(this))
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            { response ->
                Log.d("inresponse", "lol")
                var orderList = parseResponse(response.toString())
                binding.orderRecyclerView.adapter = OrderListAdapter(orderList, this)
            },
            { error ->
                error.message?.let {
                    Log.d("request error : ", it)
                } ?: run {
                    Log.d("request order : ", String(error.networkResponse.data))
                }
            })
        queue.add(jsonObjectRequest)
    }

    companion object {
        const val ORDER_LIST = "ORDER_LIST"
    }
}