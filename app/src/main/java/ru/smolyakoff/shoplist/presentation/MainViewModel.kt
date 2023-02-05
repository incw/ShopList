package ru.smolyakoff.shoplist.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.smolyakoff.shoplist.data.ShopListRepositoryImpl
import ru.smolyakoff.shoplist.domain.DeleteShopItemUseCase
import ru.smolyakoff.shoplist.domain.EditShopItemUseCase
import ru.smolyakoff.shoplist.domain.GetShopListUseCase
import ru.smolyakoff.shoplist.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()



    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopList(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopList(newItem)

    }

}