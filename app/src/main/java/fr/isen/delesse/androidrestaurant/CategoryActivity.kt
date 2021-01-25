package fr.isen.delesse.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fr.isen.delesse.androidrestaurant.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    enum class ItemType {
        ENTREES, PLATS, DESSERTS
    }

    private lateinit var binding : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CategoryActivity", "start of CategoryActivity")
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_category)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType
        Log.d("test", getCategoryTitle(selectedItem))
        binding.categoryTitle.text = getCategoryTitle(selectedItem)

    }
    private fun getCategoryTitle( item: ItemType?): String {
        return when(item) {
            ItemType.ENTREES -> getString(R.string.app_entree)
            ItemType.PLATS -> getString(R.string.app_plat)
            ItemType.DESSERTS -> getString(R.string.app_dessert)
            else -> ""
        }
    }

    override fun onDestroy() {
        Log.d("CategoryActivity", "end of CategoryActivity")
        super.onDestroy()
    }
}