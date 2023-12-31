package com.example.todo_app.ui.common

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("Error")
fun bindError(textInputLayout: TextInputLayout,error: String?)
{
    textInputLayout.error = error
}