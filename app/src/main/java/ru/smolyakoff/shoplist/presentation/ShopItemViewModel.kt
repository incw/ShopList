package ru.smolyakoff.shoplist.presentation

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.smolyakoff.shoplist.data.ShopListRepositoryImpl
import ru.smolyakoff.shoplist.domain.AddShopItemUseCase
import ru.smolyakoff.shoplist.domain.EditShopItemUseCase
import ru.smolyakoff.shoplist.domain.GetShopItemUseCase
import ru.smolyakoff.shoplist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)




    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _msgOfCloseScreen = MutableLiveData<Unit>()
    val msgOfCloseScreen: LiveData<Unit>
        get() = _msgOfCloseScreen


    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = item
        }

    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val shopItem = ShopItem(name, count, true)
                addShopItemUseCase.addShopList(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
            if (fieldsValid) {
                _shopItem.value?.let {
                    viewModelScope.launch {
                        val item = it.copy(name = name, count = count)
                        editShopItemUseCase.editShopList(item)
                        finishWork()
                    }

                }
            }

    }


    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

     fun dropErrorInputName() {
        _errorInputName.value = false
    }

     fun dropErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _msgOfCloseScreen.value = Unit
    }
    
}