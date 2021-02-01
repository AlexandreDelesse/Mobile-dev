package fr.isen.delesse.androidrestaurant.Cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.delesse.androidrestaurant.BaseActivity
import fr.isen.delesse.androidrestaurant.DishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.category.CategoryActivity
import fr.isen.delesse.androidrestaurant.category.CategoryAdapter
import fr.isen.delesse.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class CartActivity : BaseActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)

        var cart = Cart.getCart(this)
        loadCart(cart.items)
        setContentView(binding.root)
    }

    private fun loadCart(dishList: List<CartItem>?) {
        dishList?.let {
            val adapter = CartAdapter(it)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun startDishDetailActivity(dish: Dish) {
        val intent = Intent(this, DishDetailActivity::class.java)
        intent.putExtra(DishDetailActivity.DISH_EXTRA, dish)
        startActivity(intent)
    }
}