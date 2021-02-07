package fr.isen.delesse.androidrestaurant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import fr.isen.delesse.androidrestaurant.cart.Cart
import fr.isen.delesse.androidrestaurant.cart.CartActivity
import fr.isen.delesse.androidrestaurant.login.LoginActivity
import fr.isen.delesse.androidrestaurant.order.OrderActivity
import fr.isen.delesse.androidrestaurant.user.User


open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuView = menu?.findItem(R.id.cart)?.actionView
        val menuViewOrder = menu?.findItem(R.id.orders)?.actionView
        val menuViewSignout = menu?.findItem(R.id.signout)?.actionView
        val countText = menuView?.findViewById(R.id.cartMenuText) as? TextView
        val count = getItemsCount()
        countText?.isVisible = count > 0
        countText?.text = count.toString()


        menuViewSignout?.setOnClickListener {
            User.Signout(this)
            var snack = Snackbar.make(menuViewSignout, "deconnexion", Snackbar.LENGTH_SHORT)
            snack.animationMode = Snackbar.ANIMATION_MODE_SLIDE
            snack.show()
        }
        menuView?.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        menuViewOrder?.setOnClickListener{
            if(User().isConnected(this)){
                val intent = Intent(this, OrderActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }

        return true
    }

    private fun getItemsCount(): Int {
        val sharedPreferences = getSharedPreferences(Cart.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(Cart.ITEMS_COUNT, 0)
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER){
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}