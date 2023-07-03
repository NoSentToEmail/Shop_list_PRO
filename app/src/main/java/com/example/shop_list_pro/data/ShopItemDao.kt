package com.example.shop_list_pro.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shop_list_pro.domain.ShopItem

@Dao
interface ShopItemDao {
    @Query("SELECT * FROM shop_items")
    fun getShopList(): LiveData<List<ShopItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItem: ShopItem)

    @Delete
    fun deleteShopItem(shopItem: ShopItem)

    @Update
    fun editShopItem(shopItem: ShopItem)

    @Query("SELECT * FROM shop_items WHERE id = :shopItemId")
    fun getShopItem(shopItemId:Int): ShopItem


}