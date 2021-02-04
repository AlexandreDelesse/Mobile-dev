package fr.isen.delesse.androidrestaurant.order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.delesse.androidrestaurant.databinding.OrderItemCellBinding

class OrderAdapter(private val entries: Order): RecyclerView.Adapter<OrderAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int)
            : OrderAdapter.ItemViewHolder {
        return ItemViewHolder(
            OrderItemCellBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderAdapter.ItemViewHolder, position: Int) {
        val orderItem = entries.orderItems[position]
        holder.bind(orderItem)
    }

    override fun getItemCount(): Int {
        return entries.orderItems.count()
    }

    class ItemViewHolder(itemsBinding: OrderItemCellBinding)
        : RecyclerView.ViewHolder(itemsBinding.root){
        private val orderItemName: TextView = itemsBinding.textView8
        fun bind(orderItem: OrderItem) {

        }

    }
}