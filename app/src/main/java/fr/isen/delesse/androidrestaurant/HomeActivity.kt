package fr.isen.delesse.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.delesse.androidrestaurant.category.CategoryActivity
import fr.isen.delesse.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "start of HomeActivity")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardViewEntree.setOnClickListener {
            startCategoryActivity(CategoryActivity.ItemType.ENTREES)
        }
        binding.cardViewPlat.setOnClickListener {
            startCategoryActivity(CategoryActivity.ItemType.PLATS)
        }
        binding.cardViewDessert.setOnClickListener {
            startCategoryActivity(CategoryActivity.ItemType.DESSERTS)
        }
    }

    private fun startCategoryActivity(item: CategoryActivity.ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }
    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
        const val USER = "USER"
    }

    override fun onDestroy() {
        Log.d("HomeActivity", "end of HomeActivity")
        super.onDestroy()
    }
}