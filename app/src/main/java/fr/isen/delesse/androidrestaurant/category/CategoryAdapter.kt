package fr.isen.delesse.androidrestaurant.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.delesse.androidrestaurant.databinding.ItemCellBinding

class CategoryAdapter (private val entries: List<String>): RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): ItemViewHolder {
        return ItemViewHolder(ItemCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.titleView.text = entries[position]
    }

    override fun getItemCount(): Int {
        return entries.count()
    }

    class ItemViewHolder(itemsBinding: ItemCellBinding): RecyclerView.ViewHolder(itemsBinding.root) {
        val titleView: TextView = itemsBinding.itemTitle
    }
}