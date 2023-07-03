package com.example.shop_list_pro.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shop_list_pro.domain.ShopItem
import kotlinx.coroutines.InternalCoroutinesApi


@OptIn(InternalCoroutinesApi::class)
@Database(entities = [ShopItem::class], version = 1, exportSchema = false)
abstract class ShopListDataBase : RoomDatabase() {


    abstract fun shopItemDao(): ShopItemDao

    companion object {

        private var database: ShopListDataBase? = null

        private val DB_NAME = "shop_items.db"
        private val LOCK: Any = Any()

        fun getInstance(context: Context): ShopListDataBase {
            kotlinx.coroutines.internal.synchronized(LOCK) {
                database?.let { return it }
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopListDataBase::class.java,
                    DB_NAME
                ).build()
                database = instance
                return instance
            }
        }
    }
}

