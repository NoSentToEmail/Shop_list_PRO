package com.example.shop_list_pro.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class ShopListDataBase : RoomDatabase() {

    abstract fun shopItemDao(): ShopItemDao

    companion object {

        private var database: ShopListDataBase? = null

        private const val DB_NAME = "shop_items.db"
        private val LOCK: Any = Any()

        fun getInstance(application: Application): ShopListDataBase {
            database?.let { return it }
            synchronized(LOCK) {
                database?.let { return it }
                val db = Room.databaseBuilder(
                    application,
                    ShopListDataBase::class.java,
                    DB_NAME
                )
//                    .allowMainThreadQueries()  дает возможность испльзовать корутины для теста
                    .build()
                database = db
                return db
            }
        }
    }
}

