package com.example.shop_list_pro.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopList(shopItem: ShopItem){
        shopListRepository.addShopList(shopItem)
    }
}