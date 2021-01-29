package fr.isen.delesse.androidrestaurant.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.delesse.androidrestaurant.DishDetail.DishDetailActivity
import fr.isen.delesse.androidrestaurant.HomeActivity
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.delesse.androidrestaurant.network.Dish
import fr.isen.delesse.androidrestaurant.network.MenuResult
import fr.isen.delesse.androidrestaurant.network.NetworkConstant
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {

    enum class ItemType {
        ENTREES, PLATS, DESSERTS;

        fun categoryTitle(item: ItemType?): String {
            return when(item) {
                ENTREES -> "Entrées"
                PLATS -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }

    private lateinit var binding : ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CategoryActivity", "start of CategoryActivity")

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(HomeActivity.CATEGORY_NAME) as? ItemType

        binding.swipeRefresh.setOnRefreshListener {
            resetCache()
            makeRequest(selectedItem)
        }
        binding.categoryTitle.text = getCategoryTitle(selectedItem)

        makeRequest(selectedItem)
    }

    private fun makeRequest(selectedItem: ItemType?) {
        resultFromCache()?.let {
            //la requete est en cache
            parseResult(it, selectedItem)
            setContentView(binding.root)
        } ?: run {
            val queue = Volley.newRequestQueue(this)
            val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_MENU
            val jsonData = JSONObject()
            jsonData.put(NetworkConstant.ID_SHOP,1)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,
                url,
                jsonData,
                { response ->
                    binding.swipeRefresh.isRefreshing = false
                    cacheResult(response.toString())
                    parseResult(response.toString(), selectedItem)
                    setContentView(binding.root)
                },
                { error ->
                    binding.swipeRefresh.isRefreshing = false
                    error.message?.let {
                        Log.d("request error : ", it)
                    } ?: run {
                        Log.d("request : ", error.toString())
                    }
                })
            queue.add(jsonObjectRequest)
        }

    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE, response)
        editor.apply()
    }

    private fun resetCache() {
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(REQUEST_CACHE)
        editor.apply()
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }

    private fun parseResult(response: String, selectedItem: ItemType?) {
        val menuResult = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
        var item = menuResult.data.firstOrNull { it.name == getCategoryTitle(selectedItem) }
        loadList(item?.items)
    }

    private fun loadList(dishList: List<Dish>?) {
        dishList?.let {
            val adapter = CategoryAdapter(it) { dish ->
                Log.d("dish", "selected dish ${dish.name}")
                startDishDetailActivity(dish)
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun startDishDetailActivity(dish: Dish) {
        val intent = Intent(this, DishDetailActivity::class.java)
        intent.putExtra("dish", dish)
        startActivity(intent)
    }

    private fun getCategoryTitle( item: ItemType?): String {
        return when(item) {
            ItemType.ENTREES -> getString(R.string.app_entree)
            ItemType.PLATS -> getString(R.string.app_plat)
            ItemType.DESSERTS -> getString(R.string.app_dessert)
            else -> ""
        }
    }

    override fun onDestroy() {
        Log.d("CategoryActivity", "end of CategoryActivity")
        super.onDestroy()
    }

    companion object  {
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }
}