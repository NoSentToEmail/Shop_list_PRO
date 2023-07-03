package com.example.shop_list_pro.presentation.EditItemActivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop_list_pro.data.ShopListDataBase
import com.example.shop_list_pro.data.ShopListRepositoryImpl
import com.example.shop_list_pro.domain.AddShopItemUseCase
import com.example.shop_list_pro.domain.EditShopItemUseCase
import com.example.shop_list_pro.domain.GetObjectFromIDShopItemUseCase
import com.example.shop_list_pro.domain.ShopItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    //Пробуем ГПТ
    private var shopItemAdded = false

    private val database: ShopListDataBase = ShopListDataBase.getInstance(application)
    private var shopList: LiveData<List<ShopItem>> = database.shopItemDao().getShopList()


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName


    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem


    private val _finishScreen = MutableLiveData<Unit>()
    val finishScreen: LiveData<Unit>
        get() = _finishScreen

    private var getShopItemJob: Job? = null

    fun getShopItem(shopItemId: Int)  = viewModelScope.launch(Dispatchers.IO) {
        getShopItemJob?.cancel() // Отменить предыдущую задачу, если она выполняется
        getShopItemJob = viewModelScope.launch(Dispatchers.IO) {
            val item = database.shopItemDao().getShopItem(shopItemId = shopItemId)
            _shopItem.postValue(item)
        }
    }


    fun addShopItem(inputName: String?, inputCount: String?) {
        viewModelScope.launch {
            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val fieldsValid = validateInput(name, count)
            if (fieldsValid) {
                val shopItem = ShopItem(name, count, true)
                withContext(Dispatchers.IO) {
                    database.shopItemDao().addShopItem(shopItem)
                }
                resetFinishScreen()
            }
            resetFinishScreenFull()
        }
    }

    fun addShopItemElement(inputName: String?, inputCount: String?) {
        viewModelScope.launch {
            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val fieldsValid = validateInput(name, count)
            if (fieldsValid) {
                val shopItem = ShopItem(name, count, true)
                withContext(Dispatchers.IO) {
                    database.shopItemDao().addShopItem(shopItem)
                }
            }
//            shopItemAdded = true
        }
    }


    fun editShopItem(name: String?, count: String?) {
        viewModelScope.launch {
            val name = parseName(name)
            val count = parseCount(count)
            val fieldsValid = validateInput(name, count)
            if (fieldsValid) {
                withContext(Dispatchers.IO) {
                    _shopItem.value?.let {
                        val item = it.copy(name = name, count = count)
                        database.shopItemDao().editShopItem(item)
                        resetFinishScreenFull()
                    }
                }
            }
        }
    }


    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputName: String?): Int {
        return try {
            inputName?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.postValue(true)
            result = false
        }
        if (count <= 0) {
            _errorInputCount.postValue(true)
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.postValue(false)
    }

    fun resetErrorInputCount() {
        _errorInputCount.postValue(false)
    }

    fun resetFinishScreen() {
        if (shopItemAdded) {
            // Вызывать метод resetFinishScreen() только если элемент был добавлен
            _finishScreen.postValue(Unit)
        }
    }

    fun resetFinishScreenFull() {
        _finishScreen.postValue(Unit)
    }
}



