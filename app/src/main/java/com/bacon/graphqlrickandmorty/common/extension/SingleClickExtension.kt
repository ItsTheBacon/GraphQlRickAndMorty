package com.bacon.graphqlrickandmorty.common.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bacon.graphqlrickandmorty.common.utils.OnSingleClickListener


fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}