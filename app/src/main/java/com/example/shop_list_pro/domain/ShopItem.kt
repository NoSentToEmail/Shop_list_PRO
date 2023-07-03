package com.example.shop_list_pro.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shop_items")
data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID

) {
    companion object {
        const val UNDEFINED_ID = -1
    }
    @Ignore
    constructor(name: String, count: Int, enabled: Boolean):
            this(name, count,enabled, 0)
}
