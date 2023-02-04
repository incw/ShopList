package ru.smolyakoff.shoplist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopList(shopItem: ShopItem ){
        shopListRepository.editShopItem(shopItem)
    }

}