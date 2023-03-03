package ru.smolyakoff.shoplist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

      suspend fun addShopList(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}