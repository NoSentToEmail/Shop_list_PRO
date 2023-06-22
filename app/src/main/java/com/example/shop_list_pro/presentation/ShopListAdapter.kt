package com.example.shop_list_pro.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_list_pro.R
import com.example.shop_list_pro.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallBack()) {

    companion object {
         const val ITEM_TYPE_ENABLED = 1
         const val ITEM_TYPE_DISABLED = 2
         const val MAX_POOL_SIZE = 20
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null


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
        val shopItem = getItem(position)

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

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) {
            ITEM_TYPE_ENABLED
        } else {
            ITEM_TYPE_DISABLED
        }
    }

}