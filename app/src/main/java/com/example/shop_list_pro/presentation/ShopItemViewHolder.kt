package com.example.shop_list_pro.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_list_pro.R

class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tv_name =  view.findViewById<TextView>(R.id.tv_name)
    val tv_count = view.findViewById<TextView>(R.id.tv_count)
}