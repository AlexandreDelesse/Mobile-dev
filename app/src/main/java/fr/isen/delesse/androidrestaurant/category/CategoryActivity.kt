package fr.isen.delesse.androidrestaurant.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.delesse.androidrestaurant.HomeActivity
import fr.isen.delesse.androidrestaurant.R
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
        binding.categoryTitle.text = getCategoryTitle(selectedItem)

        loadList(selectedItem)
    }
    private fun loadList(item: ItemType?) {
        var entrees = listOf<String>("salade", "poêle de legume", "fruits de mer")
        var plats = listOf<String>("gratin", "pizza", "pâtes")
        var desserts = listOf<String>("gateau au chocolat", "salade de fruits", "glace")
        var entries = listOf<String>()
        when (item) {
            ItemType.ENTREES -> entries = entrees
            ItemType.PLATS -> entries = plats
            ItemType.DESSERTS -> entries = desserts
        }
        val adapter = CategoryAdapter(entries)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
    private fun getCategoryTitle( item: ItemType?): String {
        return when(item) {
            ItemType.ENTREES -> getString(
                R.string.app_entree
            )
            ItemType.PLATS -> getString(
                R.string.app_plat
            )
            ItemType.DESSERTS -> getString(
                R.string.app_dessert
            )
            else -> ""
        }
    }

    override fun onDestroy() {
        Log.d("CategoryActivity", "end of CategoryActivity")
        super.onDestroy()
    }
}