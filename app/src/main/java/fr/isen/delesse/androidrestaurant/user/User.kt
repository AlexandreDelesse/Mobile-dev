package fr.isen.delesse.androidrestaurant.user

import android.content.Context
import android.util.Log
import java.io.Serializable

class User(): Serializable {

    fun getUserId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(USER_ID, -1)
    }

    fun saveUser(idUser: Int, context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, idUser)
        editor.apply()
        Log.d("user::save user", "user $idUser have been saved in preferences")
    }

    fun isConnected(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(USER_ID, -1) != -1
    }

    companion object {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val USER_ID = "USER_ID"
    }
}