package fr.isen.delesse.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.delesse.androidrestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "start of HomeActivity")
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textEntrees.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_entrees), Toast.LENGTH_SHORT).show()
            startCategoryActivity(CategoryActivity.ItemType.ENTREES)
        }
        binding.textPlats.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_plats), Toast.LENGTH_SHORT).show()
            startCategoryActivity(CategoryActivity.ItemType.PLATS)
        }
        binding.textDesserts.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_desserts), Toast.LENGTH_SHORT).show()
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
    }

    override fun onDestroy() {
        Log.d("HomeActivity", "end of HomeActivity")
        super.onDestroy()
    }
}