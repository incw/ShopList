package ru.smolyakoff.shoplist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopList(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }

}