package com.example.shop_list_pro.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shop_list_pro.domain.ShopItem
import com.example.shop_list_pro.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private lateinit var shopListLiveData: LiveData<List<ShopItem>>
    private lateinit var database: ShopListDataBase


    private var autoIncrementId = 0
    fun initialize(context: Context) {
        database = ShopListDataBase.getInstance(context)
        shopListLiveData = database.shopItemDao().getShopList()
    }

    override fun addShopItem(shopItem: ShopItem) {
        val entity = ShopItem(name = shopItem.name, count = shopItem.count, enabled = shopItem.enabled)
        database.shopItemDao().addShopItem(entity)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        val entity = ShopItem(id = shopItem.id, name = shopItem.name, count = shopItem.count, enabled = shopItem.enabled)
        database.shopItemDao().deleteShopItem(entity)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val entity = ShopItem(id = shopItem.id, name = shopItem.name, count = shopItem.count, enabled = shopItem.enabled)
        database.shopItemDao().editShopItem(entity)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        val entity = database.shopItemDao().getShopItem(shopItemId)
        return ShopItem(entity.name, entity.count, entity.enabled, entity.id)
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }
}


//object ShopListRepositoryImpl: ShopListRepository {
//
//    private val shopListLiveData = MutableLiveData<List<ShopItem>>()
//    private val shopList = sortedSetOf<ShopItem>({
//        o1, o2 -> o1.id.compareTo(o2.id) })
//
//    private var autoIncrementId = 0
//
//    init {
//        for (i in 0 until 10){
//            val item = ShopItem("name $i", i, Random.nextBoolean())
//            addShopItem(item)
//        }
//    }
//
//
//
//
//    override fun addShopItem(shopItem: ShopItem) {
//        if(shopItem.id == ShopItem.UNDEFINED_ID){
//            shopItem.id = autoIncrementId
//            autoIncrementId++
//        }
//        shopList.add(shopItem)
//        updateList()
//    }
//
//    override fun deleteShopItem(shopItem: ShopItem) {
//        if(shopItem.id == ShopItem.UNDEFINED_ID){
//            shopItem.id = autoIncrementId
//            autoIncrementId--
//        }
//
//        shopList.remove(shopItem)
//        updateList()
//    }
//
//    override fun editShopItem(shopItem: ShopItem) {
//        val oldelement = getShopItem(shopItem.id)
//        shopList.remove(oldelement)
//        addShopItem(shopItem)
//    }
//
//    override fun getShopItem(shopItemId: Int): ShopItem {
//        return shopList.first { it.id == shopItemId }
//    }
//
//    override fun getShopList(): LiveData<List<ShopItem>> {
//        return shopListLiveData
//    }
//
//    private fun updateList(){
//        shopListLiveData.value = shopList.toList()
//    }
//}