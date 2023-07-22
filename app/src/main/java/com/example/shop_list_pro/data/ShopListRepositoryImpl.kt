package com.example.shop_list_pro.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.shop_list_pro.domain.ShopItem
import com.example.shop_list_pro.domain.ShopListRepository

class ShopListRepositoryImpl(application: Application) : ShopListRepository {


    private val shopItemDao = ShopListDataBase.getInstance(application).shopItemDao()
    private val mapper = ShopListMapper()


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopItemDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopItemDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopItemDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopItemDao.getShopList().map{
        mapper.mapListDbModelToListEntity(it)
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