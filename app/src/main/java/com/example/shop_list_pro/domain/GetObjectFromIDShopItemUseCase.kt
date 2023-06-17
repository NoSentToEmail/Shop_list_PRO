package com.example.shop_list_pro.domain

class GetObjectFromIDShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(shopItemId:Int): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}