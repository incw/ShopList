package ru.smolyakoff.shoplist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun editShopList(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}