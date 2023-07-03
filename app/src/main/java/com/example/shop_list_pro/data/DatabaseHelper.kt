package com.example.shop_list_pro.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shop_list_pro.domain.ShopItem
import kotlin.random.Random

class DatabaseHelper(
    context: Context?,
    private var DB_NAME: String = "shop_items.db",
    private var DB_VERSION: Int = 1
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val TABLE_NAME = "shop_items"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_COUNT = "count"
        private const val COLUMN_ENABLED = "enabled"

        private const val DROP_COMMAND = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_COUNT INTEGER, " +
                "$COLUMN_ENABLED INTEGER)"

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropCommand = DROP_COMMAND
        db.execSQL(dropCommand)
        onCreate(db)
    }

//    fun addShopItem(shopItem: ShopItem) {
//        val db = writableDatabase
//
//        val values = ContentValues().apply {
//            put(COLUMN_NAME, shopItem.name)
//            put(COLUMN_COUNT, shopItem.count)
//            put(COLUMN_ENABLED, if (shopItem.enabled) 1 else 0)
//        }
//
//        db.insert(TABLE_NAME, null, values)
//        db.close()
//    }
//
//    fun deleteShopItem(shopItem: ShopItem) {
//        val db = writableDatabase
//        val selection = "$COLUMN_ID = ?"
//        val selectionArgs = arrayOf(shopItem.id.toString())
//
//        db.delete(TABLE_NAME, selection, selectionArgs)
//        db.close()
//    }
//
//    fun updateShopItem(shopItem: ShopItem) {
//        val db = writableDatabase
//
//        val values = ContentValues().apply {
//            put(COLUMN_NAME, shopItem.name)
//            put(COLUMN_COUNT, shopItem.count)
//            put(COLUMN_ENABLED, if (shopItem.enabled) 1 else 0)
//        }
//
//        val selection = "$COLUMN_ID = ?"
//        val selectionArgs = arrayOf(shopItem.id.toString())
//
//        db.update(TABLE_NAME, values, selection, selectionArgs)
//        db.close()
//    }
//
//    fun getShopItem(shopItemId: Int): ShopItem? {
//        val db = readableDatabase
//
//        val columns = arrayOf(
//            COLUMN_ID,
//            COLUMN_NAME,
//            COLUMN_COUNT,
//            COLUMN_ENABLED
//        )
//        val selection = "$COLUMN_ID = ?"
//        val selectionArgs = arrayOf(shopItemId.toString())
//
//        val cursor: Cursor? = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null)
//
//        var shopItem: ShopItem? = null
//
//        cursor?.let {
//            if (cursor.moveToFirst()) {
//                val idIndex = cursor.getColumnIndex(COLUMN_ID)
//                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
//                val countIndex = cursor.getColumnIndex(COLUMN_COUNT)
//                val enabledIndex = cursor.getColumnIndex(COLUMN_ENABLED)
//
//                val id = cursor.getInt(idIndex)
//                val name = cursor.getString(nameIndex)
//                val count = cursor.getInt(countIndex)
//                val enabled = cursor.getInt(enabledIndex) == 1
//
//                shopItem = ShopItem(name, count, enabled, id)
//            }
//            cursor.close()
//        }
//
//        db.close()
//
//        return shopItem
//    }
//
//    fun getAllShopItems(): List<ShopItem> {
//        val db = readableDatabase
//
//        val columns = arrayOf(
//            COLUMN_ID,
//            COLUMN_NAME,
//            COLUMN_COUNT,
//            COLUMN_ENABLED
//        )
//
//        val cursor: Cursor? = db.query(TABLE_NAME, columns, null, null, null, null, null)
//
//        val shopItems = mutableListOf<ShopItem>()
//
//        cursor?.let {
//            while (cursor.moveToNext()) {
//                val idIndex = cursor.getColumnIndex(COLUMN_ID)
//                val nameIndex = cursor.getColumnIndex(COLUMN_NAME)
//                val countIndex = cursor.getColumnIndex(COLUMN_COUNT)
//                val enabledIndex = cursor.getColumnIndex(COLUMN_ENABLED)
//
//                val id = cursor.getInt(idIndex)
//                val name = cursor.getString(nameIndex)
//                val count = cursor.getInt(countIndex)
//                val enabled = cursor.getInt(enabledIndex) == 1
//
//                val shopItem = ShopItem(name, count, enabled, id)
//                shopItems.add(shopItem)
//            }
//            cursor.close()
//        }
//
//        db.close()
//
//        return shopItems
//    }
}

