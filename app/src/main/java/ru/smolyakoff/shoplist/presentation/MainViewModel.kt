package ru.smolyakoff.shoplist.presentation


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.smolyakoff.shoplist.data.ShopListRepositoryImpl
import ru.smolyakoff.shoplist.domain.DeleteShopItemUseCase
import ru.smolyakoff.shoplist.domain.EditShopItemUseCase
import ru.smolyakoff.shoplist.domain.GetShopListUseCase
import ru.smolyakoff.shoplist.domain.ShopItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)


    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopList(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopList(newItem)
        }
    }


}