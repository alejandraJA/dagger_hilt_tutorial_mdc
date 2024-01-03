package com.example.dagger_hilt.sys.util;

import com.google.android.material.textfield.TextInputLayout;

public class UtilsText {
    private final TextInputLayout editText;

    public UtilsText(TextInputLayout editText) {
        this.editText = editText;
    }

    public Boolean isNotBlank() {
        var result = !editText.getEditText().getText().toString().isEmpty();
        editText.setError(result ? null : "Campo requerido");
        return result;
    }
}
