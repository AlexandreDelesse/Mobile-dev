package fr.isen.delesse.androidrestaurant.DishDetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.BaseActivity
import fr.isen.delesse.androidrestaurant.Cart.Cart
import fr.isen.delesse.androidrestaurant.Cart.CartItem
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.category.CategoryActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class DishDetailActivity : BaseActivity() {
    private lateinit var binding : ActivityDishDetailBinding
    private var countValue = 0

    companion object {
        const val DISH_EXTRA = "DISH_EXTRA"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_dish_detail)

        val dishDetails = intent.getSerializableExtra(DISH_EXTRA) as Dish
        var url: String? = null

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
        /*if (dishDetails.images.isNotEmpty()){
            url = dishDetails.images.first()
        }
        Picasso.get().load(url).placeholder(R.drawable.icondessert).into(binding.dishDetailImage)*/
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
        binding.countTextView.text = countValue.toString()
        binding.totalButton.text = "TOTAL : ${countAndShowTotal(dish).toString()} €"
    }

    private fun removeOne(dish: Dish) {
        if(this.countValue > 0) {
            this.countValue--
            binding.countTextView.text = countValue.toString()
            binding.totalButton.text = "TOTAL : ${countAndShowTotal(dish).toString()} €"
        }
    }

    private fun countAndShowTotal(dish: Dish): Float {
        return dish.prices.first().price.toFloat() * this.countValue.toFloat()
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

    private fun afficheAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.cart_Validation))
        builder.setPositiveButton("ok") { dioalog, which ->

        }
        builder.show()
    }

    private fun refreshMenu(cart: Cart) {
        val count = cart.itemCount
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, count)
        editor.apply()
        invalidateOptionsMenu()
    }
}