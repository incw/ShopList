package ru.smolyakoff.shoplist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(shopItemID: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemID)
    }

}