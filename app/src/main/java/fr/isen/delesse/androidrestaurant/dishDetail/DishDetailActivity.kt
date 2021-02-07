package fr.isen.delesse.androidrestaurant.dishDetail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.BaseActivity
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.cart.Cart
import fr.isen.delesse.androidrestaurant.cart.CartItem
import fr.isen.delesse.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class DishDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityDishDetailBinding
    private var countValue = 1

    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_dish_detail)

        val dishDetails = intent.getSerializableExtra(DISH_EXTRA) as Dish
        refreshTotal(dishDetails)

        binding.plusButton.setOnClickListener{
            addOne(dishDetails)
        }
        binding.lessButton.setOnClickListener {
            removeOne(dishDetails)
        }
        binding.dishDetailName.text = dishDetails.name
        dishDetails.ingredients.forEach {
            binding.dishDetailIngredient.append(it.name)
            binding.dishDetailIngredient.append(", ")
        }
        binding.dishDetailPrice.text = "${dishDetails.prices.first().price}. €"
        binding.viewPager.adapter = DishDetailAdapter(this, dishDetails.images)
        binding.totalButton.setOnClickListener {
            var cart = Cart.getCart(this)
            val existingItem = cart.items.firstOrNull {
                it.dish.name == dishDetails.name
            }
            existingItem?.let {
                existingItem.count = countValue
            }?: run {
                binding.countTextView.text = countValue.toString()
            }
            addToCart(dishDetails, countValue) }
    }

    private fun addOne(dish: Dish) {
        this.countValue++
        refreshTotal(dish)
    }

    private fun removeOne(dish: Dish) {
        //countValue = max(1, countValue -1)
        if(this.countValue > 1) {
            this.countValue--
        }
        refreshTotal(dish)
    }

    private fun refreshTotal(dish: Dish) {
        var result = dish.prices.first().price.toFloat() * this.countValue.toFloat()
        binding.countTextView.text = countValue.toString()
        binding.totalButton.text = "TOTAL : ${result} €"
    }

    private fun addToCart(dish: Dish, count: Int) {
        val cart = Cart.getCart(this)
        // recuperer cart fichier
        // s'il existe pas, le créer
        // le mettre a jour
        cart.addItem(CartItem(dish, count))
        cart.save(this)
        val json = GsonBuilder().create().toJson(cart)
        refreshMenu(cart)
        Snackbar.make(binding.root, getString(R.string.cart_Validation), Snackbar.LENGTH_SHORT).show()

    }


    private fun refreshMenu(cart: Cart) {
        invalidateOptionsMenu()
    }
}