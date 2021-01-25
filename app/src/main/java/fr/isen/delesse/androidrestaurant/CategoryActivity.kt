package fr.isen.delesse.androidrestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class CategoryActivity : AppCompatActivity() {

    enum class ItemType {
        ENTREES, PLATS, DESSERTS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("CategoryActivity", "start of CategoryActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }

    override fun onDestroy() {
        Log.d("CategoryActivity", "end of CategoryActivity")
        super.onDestroy()
    }
}