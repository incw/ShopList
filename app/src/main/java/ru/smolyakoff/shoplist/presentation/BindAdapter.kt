package ru.smolyakoff.shoplist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import ru.smolyakoff.shoplist.R

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_invalid_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_invalid_count)
    } else {
        null
    }
    textInputLayout.error = message
}