package com.example.shop_list_pro.domain

interface ShopListRepository {
    fun addShopList(shopItem: ShopItem)

    fun deleteShopItem(shopItem: ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId:Int): ShopItem

    fun getShopList(): List<ShopItem>
}