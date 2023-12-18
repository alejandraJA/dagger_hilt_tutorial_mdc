package com.example.dagger_hilt.sys.util

import com.google.android.material.textfield.TextInputLayout

object UtilsTextKts {
    fun TextInputLayout.isNotBlank(): Boolean {
        var result = false
        error = if (editText!!.text.toString().isNotBlank()) {
            result = true
            null
        } else "Campo requerido"
        return result
    }
}