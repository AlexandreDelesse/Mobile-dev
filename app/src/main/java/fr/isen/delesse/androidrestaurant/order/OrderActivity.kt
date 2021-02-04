package fr.isen.delesse.androidrestaurant.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.databinding.ActivityOrderBinding
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import org.json.JSONObject

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun makeRequest(jsonOrder: JSONObject) {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonOrder,
            { response ->
                Log.d("response order", response.toString())
                var snackbar = Snackbar.make(binding.root, "commande envoyé avec succés", Snackbar.LENGTH_SHORT)
                snackbar.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
            },
            { error ->
                error.message?.let {
                    Log.d("request error : ", it)
                } ?: run {
                    Log.d("request order : ", String(error.networkResponse.data))
                }
            })
        queue.add(jsonObjectRequest)
    }

    companion object {

    }
}