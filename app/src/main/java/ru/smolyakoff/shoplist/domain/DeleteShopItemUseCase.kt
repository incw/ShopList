package ru.smolyakoff.shoplist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopList(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }

}