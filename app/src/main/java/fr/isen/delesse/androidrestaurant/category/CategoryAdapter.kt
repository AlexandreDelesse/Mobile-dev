package fr.isen.delesse.androidrestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.delesse.androidrestaurant.databinding.ItemCellBinding
import fr.isen.delesse.androidrestaurant.network.Dish

class CategoryAdapter (private val entries: List<Dish>,
                        private val entryClickListener: (Dish) -> Unit)
    : RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
        return ItemViewHolder(ItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dish = entries[position]
        holder.layout.setOnClickListener {
            entryClickListener.invoke(dish)
        }
        holder.bind(dish)
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class ItemViewHolder(itemsBinding: ItemCellBinding): RecyclerView.ViewHolder(itemsBinding.root) {
        val titleView: TextView = itemsBinding.itemTitle
        val priceView: TextView = itemsBinding.itemPrice
        val imageView: ImageView = itemsBinding.itemImageView
        val layout = itemsBinding.root

        fun bind(dish: Dish) {
            titleView.text = dish.name
            priceView.text = "${dish.prices.first().price} €"
        }
    }
}