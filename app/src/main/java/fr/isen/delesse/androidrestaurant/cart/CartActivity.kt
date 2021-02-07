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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.dishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.delesse.androidrestaurant.login.RegisterActivity
import fr.isen.delesse.androidrestaurant.network.Dish
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import fr.isen.delesse.androidrestaurant.order.Order
import fr.isen.delesse.androidrestaurant.order.OrderActivity
import fr.isen.delesse.androidrestaurant.user.User
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

            if(Cart.getCart(this).itemCount > 0) {
                if(User().isConnected(this)){
                    sendOrder(User.getUserId(this))
                    Cart.deleteCart(this)
                    val intent = Intent(this, OrderActivity::class.java)
                    //startActivity(intent)
                } else {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE)
                }
            } else {
                Snackbar.make(binding.root, "votre panier est vide", Snackbar.LENGTH_SHORT).show()
            }
            reloadData(Cart.getCart(this))
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
        var totalOrder: Float = 0F
        cart.items.forEach{
            totalOrder = it.count * it.dish.prices.first().price.toFloat()
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
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun sendOrder (idUser: Int){
        var cart = Cart.getCart(this)
        var jsonOrder = parseCart(cart, idUser)
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
                Log.d("response after order", response.toString())
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra(OrderActivity.ORDER_LIST, response.toString())
                startActivity(intent)
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