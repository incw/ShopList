package ru.smolyakoff.shoplist.domain

interface ShopListRepository {


    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItem:ShopItem)

    fun editShopItem(shopItem: ShopItem )

    fun getShopItem(shopItemID:Int): ShopItem

    fun getShopList(): List<ShopItem>

}