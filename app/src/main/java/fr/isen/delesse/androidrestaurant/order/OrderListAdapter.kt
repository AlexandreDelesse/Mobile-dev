package fr.isen.delesse.androidrestaurant.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.delesse.androidrestaurant.cart.Cart
import fr.isen.delesse.androidrestaurant.cart.CartItem
import fr.isen.delesse.androidrestaurant.databinding.OrderItemCellBinding
import fr.isen.delesse.androidrestaurant.databinding.OrderListItemCellBinding

class OrderListAdapter (private val entries: MutableList<Cart>,
private val context: Context
): RecyclerView.Adapter<OrderListAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int)
            : OrderListAdapter.ItemViewHolder {
        return ItemViewHolder(
            OrderListItemCellBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val orderItem = entries[position]
        holder.bind(orderItem, context)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class ItemViewHolder(itemsBinding: OrderListItemCellBinding)
        : RecyclerView.ViewHolder(itemsBinding.root){

        val recycler: RecyclerView = itemsBinding.orderListRecyclerView
        val textView: TextView = itemsBinding.textView7
        val tewtviewtot: TextView = itemsBinding.textView9
        

        fun bind(cart: Cart, context: Context) {
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = OrderAdapter(cart)
            textView.text = "${cart.itemCount} articles"

            tewtviewtot.text = "${cart.getTotal()} €"
        }

    }
}