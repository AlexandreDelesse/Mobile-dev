package fr.isen.delesse.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.databinding.ActivityCategoryBinding
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
        if (dishDetails.images.isNotEmpty()){
            url = dishDetails.images.first()
        }
        Picasso.get().load(url).placeholder(R.drawable.icondessert).into(binding.dishDetailImage)
    }

    private fun addOne(dish: Dish) {
        this.countValue++
        binding.countTextView.text = countValue.toString()
        binding.totalButton.text = "${countAndShowTotal(dish).toString()} €"
    }

    private fun removeOne(dish: Dish) {
        if(this.countValue > 0) {
            this.countValue--
            binding.countTextView.text = countValue.toString()
            binding.totalButton.text = "${countAndShowTotal(dish).toString()} €"
        }
    }
    private fun countAndShowTotal(dish: Dish): Float {

        return dish.prices.first().price.toFloat() * this.countValue.toFloat()
    }
}