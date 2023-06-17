package com.example.shop_list_pro.domain

data class ShopItem(

    val name: Int,
    val count: Int,
    val enable: Boolean,

    var id: Int = UNDEFINED_ID

) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
