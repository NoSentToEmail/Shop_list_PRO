package com.example.shop_list_pro.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun addShopList(shopItem: ShopItem){
        shopListRepository.addShopItem(shopItem)
    }
}