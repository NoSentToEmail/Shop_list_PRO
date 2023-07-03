package com.example.shop_list_pro.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_list_pro.data.ShopItemDao
import com.example.shop_list_pro.data.ShopListDataBase
import com.example.shop_list_pro.data.ShopListRepositoryImpl
import com.example.shop_list_pro.domain.AddShopItemUseCase
import com.example.shop_list_pro.domain.DeleteShopItemUseCase
import com.example.shop_list_pro.domain.EditShopItemUseCase
import com.example.shop_list_pro.domain.GetShopListUseCase
import com.example.shop_list_pro.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database: ShopListDataBase = ShopListDataBase.getInstance(application)
    var shopList: LiveData<List<ShopItem>> = database.shopItemDao().getShopList()




    fun deletShopItem(shopItem: ShopItem) = viewModelScope.launch(Dispatchers.IO) {
        database.shopItemDao().deleteShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) = viewModelScope.launch(Dispatchers.IO) {
        val edit = shopItem.copy(enabled = !shopItem.enabled)
        database.shopItemDao().editShopItem(edit)
    }

    fun changeEnableState(shopItem: ShopItem)= viewModelScope.launch(Dispatchers.IO) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        database.shopItemDao().editShopItem(newItem)
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