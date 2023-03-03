package ru.smolyakoff.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.smolyakoff.shoplist.R
import ru.smolyakoff.shoplist.databinding.FragmentShopItemBinding
import ru.smolyakoff.shoplist.domain.ShopItem

class ShopItemFragment : Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemID: Int = ShopItem.UNDEFINED_ID

    private lateinit var onEditingFinishListener: OnEditingFinishListener

    private lateinit var viewModel: ShopItemViewModel




    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement listener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChange()
        launchRightMode()
        observeViewModel()
    }


    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun observeViewModel() {
        viewModel.msgOfCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener.onEditingFinish()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemID)
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(binding.etName.text?.toString(),
                binding.etCount.text.toString())
        }
    }


    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(binding.etName.text?.toString(),
                binding.etCount.text.toString())
        }
    }

    private fun parseParam() {
        val args = requireArguments()

        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is null")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("$mode is unknown mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {

            if (!args.containsKey(ITEM_ID)) {
                throw RuntimeException("Shop item ID is null")
            }
            shopItemID = args.getInt(ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun addTextChange() {

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.dropErrorInputName()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.dropErrorInputCount()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    interface OnEditingFinishListener {

        fun onEditingFinish() {

        }

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val ITEM_ID = "extra_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemID: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(ITEM_ID, shopItemID)
                }
            }
        }
    }
}
