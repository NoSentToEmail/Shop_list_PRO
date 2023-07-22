package com.example.shop_list_pro.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_list_pro.data.ShopListRepositoryImpl
import com.example.shop_list_pro.domain.DeleteShopItemUseCase
import com.example.shop_list_pro.domain.EditShopItemUseCase
import com.example.shop_list_pro.domain.GetShopListUseCase
import com.example.shop_list_pro.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deletShopItem(shopItem: ShopItem){
        viewModelScope.launch(Dispatchers.IO) {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}


//class MainViewModel: ViewModel() {
//
//    private val repository = ShopListRepositoryImpl
//
//    private val getShopListUseCase = GetShopListUseCase(repository)
//    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
//    private val editShopItemUseCase = EditShopItemUseCase(repository)
//    private val addShopItemUseCase = AddShopItemUseCase(repository)
//
//    val shopList = getShopListUseCase.getShopList()
//
//
//    fun deletShopItem(shopItem: ShopItem){
//        deleteShopItemUseCase.deleteShopItem(shopItem)
//    }
//
//    fun editShopItem(shopItem: ShopItem){
//        val edit = shopItem.copy(enabled = !shopItem.enabled)
//        editShopItemUseCase.editShopItem(edit)
//    }
//
//    fun changeEnableState(shopItem: ShopItem) {
//        val newItem = shopItem.copy(enabled = !shopItem.enabled)
//        editShopItemUseCase.editShopItem(newItem)
//    }
//
//}