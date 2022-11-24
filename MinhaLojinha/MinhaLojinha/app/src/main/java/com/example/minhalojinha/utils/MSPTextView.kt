package com.example.minhalojinha.utils

import android.content.Context
import android.util.AttributeSet
import android.graphics.Typeface
import androidx.appcompat.widget.AppCompatTextView


class MSPTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    init {
        //chama essa funcao p aplicar a fonte ao componente
        applyFont()
    }

    private fun applyFont() {
        //isso é usado p pegar o file do folder de assets e setar ele pro titulo do textView
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}