package fr.isen.delesse.androidrestaurant.DishDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.Cart.CartItem
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.databinding.ActivityDishDetailBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class DishDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDishDetailBinding
    private var countValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_dish_detail)

        val dishDetails = intent.getSerializableExtra("dish") as Dish
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
        addToCart(dish, countValue)
        return dish.prices.first().price.toFloat() * this.countValue.toFloat()

    }
    private fun addToCart(dish: Dish, count: Int) {
        // recuperer cart fichier
        // s'il existe pas, le créer
        // le mettre a jour
        val item = CartItem(dish, count)
        val json = GsonBuilder().create().toJson(item)
        Log.d("cart", json)
    }
}