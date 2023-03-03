package ru.smolyakoff.shoplist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {


    suspend fun addShopItem(shopItem: ShopItem)

    suspend fun deleteShopItem(shopItem: ShopItem)

    suspend fun editShopItem(shopItem: ShopItem)

    suspend fun getShopItem(shopItemID: Int): ShopItem

     fun getShopList(): LiveData<List<ShopItem>>

}