package fr.isen.delesse.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        binding.dishDetailName.text = detailName
    }
}