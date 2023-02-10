package ru.smolyakoff.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.smolyakoff.shoplist.R
import ru.smolyakoff.shoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(),ShopItemFragment.OnEditingFinishListener {


    private var screenMode = MODE_UNKNOWN

    private var shopItemID = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()

        if (savedInstanceState == null){
            launchRightMode()
        }
    }

    override fun onEditingFinish() {
        finish()
    }

    private fun launchRightMode() {

        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemID)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is null")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("$mode is unknown mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {

            if (!intent.hasExtra(EXTRA_ID)) {

                throw RuntimeException("Shop item ID is NULL")

            }
            shopItemID = intent.getIntExtra(EXTRA_ID, ShopItem.UNDEFINED_ID)
        }
    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_ID = "extra_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ID, shopItemId)
            return intent
        }


    }
}