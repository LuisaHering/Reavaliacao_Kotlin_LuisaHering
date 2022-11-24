package com.example.minhalojinha.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

/**
 * essa classe sera usada pra Custom font text usando o botao que hera o AppCompatButton class.
 */
class MSPButton(context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {

    /**
     * esse init block runs toda vez que a classe for instanciada
     */
    init {
        // Call the function to apply the font to the components.
        applyFont()
    }

    /**
     * aplica uma fonte ao botao
     */
    private fun applyFont() {

        // This is used to get the file from the assets folder and set it to the title textView.
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)

    }
}