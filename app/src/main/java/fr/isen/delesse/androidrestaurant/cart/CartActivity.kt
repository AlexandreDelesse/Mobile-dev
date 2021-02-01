package fr.isen.delesse.androidrestaurant.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.delesse.androidrestaurant.dishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.delesse.androidrestaurant.network.Dish



class CartActivity : AppCompatActivity(), CartCellInterface {
    private lateinit var binding: ActivityCartBinding
    //TODO late init for get basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)

        var cart = Cart.getCart(this)
        setContentView(binding.root)

        reloadData(cart)
    }

    private fun reloadData(cart: Cart){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CartAdapter(cart, this, this)
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

    override fun onShowDetail(item: CartItem) {
        TODO("Not yet implemented")
    }
}