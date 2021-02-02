package fr.isen.delesse.androidrestaurant.cart

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import fr.isen.delesse.androidrestaurant.dishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.delesse.androidrestaurant.login.RegisterActivity
import fr.isen.delesse.androidrestaurant.network.Dish
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import fr.isen.delesse.androidrestaurant.network.RegisterResult
import org.json.JSONArray
import org.json.JSONObject


class CartActivity : AppCompatActivity(), CartCellInterface {
    private lateinit var binding: ActivityCartBinding

    companion object {
        const val REQUEST_CODE = 100
    }
    //TODO late init for get basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)

        var cart = Cart.getCart(this)
        setContentView(binding.root)
        reloadData(cart)
        parseCart(cart, 1)

        binding.cartOrderButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private fun reloadData(cart: Cart){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CartAdapter(cart, this, this)
        displayTotalCartDetails(cart)
    }

    private fun startDishDetailActivity(dish: Dish) {
        val intent = Intent(this, DishDetailActivity::class.java)
        intent.putExtra(DishDetailActivity.DISH_EXTRA, dish)
        startActivity(intent)
    }

    override fun onDeleteItem(item: CartItem) {
        val cart = Cart.getCart(this)
        val itemToDelete = cart.items.firstOrNull { it.dish.name == item.dish.name }

        cart.items.remove(itemToDelete)
        cart.save(this)
        reloadData(cart)
    }

    private fun displayTotalCartDetails(cart: Cart){
        var totalOrder: Int = 0
        cart.items.forEach{
            totalOrder += it.count * it.dish.prices.first().price.toInt()
        }
        binding.cartQuantity.text = "${cart.itemCount} arcticles"
        binding.cartTotal.text =  "${totalOrder} €"
    }

    override fun onShowDetail(item: CartItem) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER){
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if(idUser != -1){
                sendOrder(idUser)
            }
        }
    }

    private fun sendOrder (idUser: Int){
        var cart = Cart.getCart(this)
        var jsonOrder = parseCart(cart, idUser)
        Log.d("order", jsonOrder.toString())
        makeRequest(jsonOrder)

    }

    private fun parseCart(cart: Cart, idUser: Int): JSONObject{
        val jsonOrder = JSONObject()
        jsonOrder.put(NetworkConstant.ID_SHOP, 1)
        jsonOrder.put("id_user", idUser)
        jsonOrder.put("msg", GsonBuilder().create().toJson(cart))
        return jsonOrder


    }

    private fun makeRequest(jsonOrder: JSONObject) {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonOrder,
            { response ->
                Log.d("response order", response.toString())
                Toast.makeText(this, "Votre commande a ete envoyé", Toast.LENGTH_SHORT).show()
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
}