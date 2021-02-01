package fr.isen.delesse.androidrestaurant

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.delesse.androidrestaurant.Cart.Cart
import fr.isen.delesse.androidrestaurant.Cart.CartActivity
import fr.isen.delesse.androidrestaurant.DishDetail.DishDetailActivity


open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.cart)?.actionView
        val countText = menuView?.findViewById(R.id.cartMenuText) as? TextView
        val count = getItemsCount()
        countText?.text = count.toString()


        menuView?.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(DishDetailActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(DishDetailActivity.ITEMS_COUNT, 0)
    }
}