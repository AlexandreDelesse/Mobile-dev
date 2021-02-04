package fr.isen.delesse.androidrestaurant.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.cart.CartActivity


import fr.isen.delesse.androidrestaurant.databinding.ActivityRegisterBinding
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener{
            if(testLoginForm()){
                makeRequest()
            } else {
                Toast.makeText(this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
        binding.goToLoginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, CartActivity.REQUEST_CODE)
        }

    }

    private fun testLoginForm(): Boolean{
         return (binding.loginFisrtname.text.isNotEmpty() &&
            binding.loginLastname.text.isNotEmpty() &&

            binding.loginEmail.text.isNotEmpty() &&
            binding.loginPassword.text.length >= 6)
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_REGISTER
        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP,1)
        jsonData.put(NetworkConstant.USER_FIRSTNAME, binding.loginFisrtname.text)
        jsonData.put(NetworkConstant.USER_LASTNAME, binding.loginLastname.text)
        jsonData.put(NetworkConstant.USER_ADDRESS, binding.loginAddress.text)
        jsonData.put(NetworkConstant.USER_EMAIL, binding.loginEmail.text)
        jsonData.put(NetworkConstant.USER_PASSWORD, binding.loginPassword.text)
        Log.d("Data", jsonData.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                setResult(Activity.RESULT_FIRST_USER)
                finish()
            },
            { error ->
                error.message?.let {
                    Log.d("request error : ", it)
                } ?: run {
                    Log.d("request register : ", String(error.networkResponse.data))
                }
            })
        queue.add(jsonObjectRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CartActivity.REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER){
            setResult(Activity.RESULT_FIRST_USER)
            finish()
        }
    }

    companion object {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val ID_USER = "ID_USER"
    }


}