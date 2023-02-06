package ru.smolyakoff.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.smolyakoff.shoplist.domain.ShopItem
import ru.smolyakoff.shoplist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {

    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private val shopList = sortedSetOf<ShopItem>({ p0, p1 -> p0.id.compareTo(p1.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 30) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }


    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemID: Int): ShopItem {
        return shopList.find {
            it.id == shopItemID
        } ?: throw RuntimeException("Element with id $shopItemID not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateList() {
        shopListLiveData.value = shopList.toList()
    }

}