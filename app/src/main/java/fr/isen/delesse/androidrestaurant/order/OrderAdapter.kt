package fr.isen.delesse.androidrestaurant.order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.delesse.androidrestaurant.cart.Cart
import fr.isen.delesse.androidrestaurant.cart.CartItem
import fr.isen.delesse.androidrestaurant.databinding.OrderItemCellBinding

class OrderAdapter(private val entries: Cart): RecyclerView.Adapter<OrderAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int)
            : OrderAdapter.ItemViewHolder {
        return ItemViewHolder(
            OrderItemCellBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val orderItem = entries.items[position]
        holder.bind(orderItem)
    }

    override fun getItemCount(): Int {
        return entries.items.count()
    }

    class ItemViewHolder(itemsBinding: OrderItemCellBinding)
        : RecyclerView.ViewHolder(itemsBinding.root){
        private val orderItemName: TextView = itemsBinding.textView8
        private val orderItemNb: TextView = itemsBinding.textView6
        fun bind(orderItem: CartItem) {
            orderItemName.text = orderItem.dish.name
            orderItemNb.text = orderItem.count.toString()
        }
    }
}