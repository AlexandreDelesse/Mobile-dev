package fr.isen.delesse.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.delesse.androidrestaurant.databinding.ActivityDishDetailBinding

class DishDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDishDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_dish_detail)

        val detailName = intent.getSerializableExtra("dishName") as String
        val detailImage = intent.getSerializableExtra("dishImage") as String
        var url: String? = null
        val detailIngredient = intent.getSerializableExtra("dishIngredient") as String
        val detailPrice = intent.getSerializableExtra("dishPrice") as String
        binding.dishDetailName.text = detailName
        binding.dishDetailIngredient.text = detailIngredient
        binding.dishDetailPrice.text = "${detailPrice}. €"
        if (detailImage.isNotEmpty()){
            url = detailImage
        }
        Picasso.get().load(url).placeholder(R.drawable.icondessert).into(binding.dishDetailImage)
    }
}