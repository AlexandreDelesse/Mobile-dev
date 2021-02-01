package fr.isen.delesse.androidrestaurant.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.delesse.androidrestaurant.dishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.delesse.androidrestaurant.login.LoginActivity
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

        binding.cartOrderButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
}