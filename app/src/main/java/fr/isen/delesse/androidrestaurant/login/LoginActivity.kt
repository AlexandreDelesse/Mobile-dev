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
import fr.isen.delesse.androidrestaurant.databinding.ActivityLoginBinding
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import fr.isen.delesse.androidrestaurant.network.RegisterResult
import fr.isen.delesse.androidrestaurant.network.User
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
                val userResult = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                saveUser(userResult.data)
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

    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(RegisterActivity.ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }

    companion object {
        const val REQUEST_CODE = -1
    }
}