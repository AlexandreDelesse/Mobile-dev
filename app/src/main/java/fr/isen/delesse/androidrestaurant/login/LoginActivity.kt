package fr.isen.delesse.androidrestaurant.login

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import fr.isen.delesse.androidrestaurant.databinding.ActivityLoginBinding
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import fr.isen.delesse.androidrestaurant.user.User
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginUserButton.setOnClickListener{
            if(testLoginForm()){
                makeRequest()
            }
        }
        binding.loginCreateAccount.setOnClickListener {
            finish()
        }

    }

    private fun testLoginForm(): Boolean{
        return (binding.userLogin.text.isNotEmpty() &&
                binding.userPassword.text.length >= 6)
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_LOGIN
        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP,1)
        jsonData.put(NetworkConstant.USER_EMAIL, binding.userLogin.text)
        jsonData.put(NetworkConstant.USER_PASSWORD, binding.userPassword.text)
        Log.d("Data login", jsonData.toString())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                Log.d("response login", response.toString())
                val userId = getUserIdFromResponse(response)
                User().saveUser(userId, this)
                setResult(Activity.RESULT_FIRST_USER)
                finish()

            },
            { error ->
                error.message?.let {
                    Log.d("request error : ", it)
                } ?: run {
                    Log.d("request login : ", String(error.networkResponse.data))
                }
            })
        queue.add(jsonObjectRequest)
    }
    fun getUserIdFromResponse(response: JSONObject): Int{
        return response.getJSONObject(RESPONSE_DATA).getInt(RESPONSE_DATA_ID)
    }


    companion object {
        const val REQUEST_CODE = -1
        const val RESPONSE_DATA = "data"
        const val RESPONSE_DATA_ID = "id"
    }
}