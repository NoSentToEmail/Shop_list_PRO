package com.example.shop_list_pro.presentation.EditItemActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_list_pro.data.ShopListRepositoryImpl
import com.example.shop_list_pro.domain.AddShopItemUseCase
import com.example.shop_list_pro.domain.EditShopItemUseCase
import com.example.shop_list_pro.domain.GetObjectFromIDShopItemUseCase
import com.example.shop_list_pro.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    //Пробуем ГПТ
    private var shopItemAdded = false


    private val repository = ShopListRepositoryImpl

    private val getShopItemtUseCase = GetObjectFromIDShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)


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


    fun getShopItem(shopItemId: Int) {
        val item = getShopItemtUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopList(shopItem)
            resetFinishScreen()
        }
        resetFinishScreenFull()

    }

    fun addShopItemElement(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopList(shopItem)

        }
        shopItemAdded = true

    }


    fun editShopItem(name: String?, count: String?) {
        val name = parseName(name)
        val count = parseCount(count)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                resetFinishScreenFull()
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
            //    Toast.makeText(this, "ERROR input Count $e", Toast.LENGTH_SHORT).show()
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            //  Toast.makeText(this, "ERROR input Name", Toast.LENGTH_SHORT).show()
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            //    Toast.makeText(this, "ERROR input Count", Toast.LENGTH_SHORT).show()
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun resetFinishScreen() {
        if (shopItemAdded) {
            // Вызывать метод resetFinishScreen() только если элемент был добавлен
            _finishScreen.value = Unit
        }
    }

    fun resetFinishScreenFull() {
        _finishScreen.value = Unit
    }
}

