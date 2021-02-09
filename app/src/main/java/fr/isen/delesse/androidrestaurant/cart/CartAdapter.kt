package fr.isen.delesse.androidrestaurant.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.R
import fr.isen.delesse.androidrestaurant.databinding.CartItemCellBinding

interface CartCellInterface{
    fun onDeleteItem(item: CartItem)
}

class CartAdapter(private val entries: Cart,
                    private val context: Context,
                    private val delegate: CartCellInterface)

    : RecyclerView.Adapter<CartAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            CartItemCellBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: CartAdapter.ItemViewHolder, position: Int) {
        val cartItem = entries.items[position]

        holder.bind(cartItem, delegate)
    }

    override fun getItemCount(): Int {
        return entries.items.count()
    }

    class ItemViewHolder(itemsBinding: CartItemCellBinding)
        : RecyclerView.ViewHolder(itemsBinding.root) {
        private val cartItemName: TextView = itemsBinding.cartItemName
        private val cartItemNb: TextView = itemsBinding.cartItemQuantity
        private val cartItemPrice: TextView = itemsBinding.cartItemPrice
        private val cartItemImage: ImageView = itemsBinding.cartItemImage
        private val deleteButton: ImageView = itemsBinding.cartItemDelete

        fun bind(cartItem: CartItem, delegate: CartCellInterface) {
            cartItemName.text = cartItem.dish.name
            cartItemNb.text ="Quantity: ${cartItem.count}"
            //TODO afficher le bon prix sur le panier
            cartItemPrice.text = "${cartItem.dish.prices.first().price} €"

            var url: String? = null
            if(cartItem.dish.images.isNotEmpty() && cartItem.dish.images[0].isNotEmpty()) {
                url = cartItem.dish.images[0]
            }
            Picasso.get().load(url).placeholder(R.drawable.icondessert).resize(70, 70).into(cartItemImage)

            deleteButton.setOnClickListener{
                delegate.onDeleteItem(cartItem)
            }

        }

    }
}