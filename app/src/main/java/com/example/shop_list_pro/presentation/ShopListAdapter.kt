package com.example.shop_list_pro.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_list_pro.R
import com.example.shop_list_pro.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
         const val ITEM_TYPE_ENABLED = 1
         const val ITEM_TYPE_DISABLED = 2
         const val MAX_POOL_SIZE = 20
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tv_name =  view.findViewById<TextView>(R.id.tv_name)
        val tv_count = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutRes = when (viewType) {
            ITEM_TYPE_ENABLED -> R.layout.item_shop_enable
            ITEM_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }

        holder.tv_name.text = shopItem.name
        holder.tv_count.text = shopItem.count.toString()
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tv_name.text = ""
        holder.tv_count.text = ""
        holder.tv_name.setTextColor(
            ContextCompat.getColor(
                holder.view.context,
                android.R.color.white
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled) {
            ITEM_TYPE_ENABLED
        } else {
            ITEM_TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
    }
}