package fr.isen.delesse.androidrestaurant.Cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.delesse.androidrestaurant.databinding.ItemCellBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class CartAdapter(private val entries: List<CartItem>)

    : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemCellBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: CartAdapter.ItemViewHolder, position: Int) {
        val cartItem = entries[position]

        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class ItemViewHolder(itemsBinding: ItemCellBinding)
        : RecyclerView.ViewHolder(itemsBinding.root) {
        val cartItemTitle: TextView = itemsBinding.itemTitle
        val cartItemCount: TextView = itemsBinding.itemPrice

        fun bind(dish: CartItem) {
            cartItemTitle.text = dish.dish.name
            cartItemCount.text = dish.count.toString()
        }

    }
}