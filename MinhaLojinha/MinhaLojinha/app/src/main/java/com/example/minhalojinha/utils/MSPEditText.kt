package com.example.minhalojinha.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

/**
 * essa classe vai ser usada pra fontes Custom usando o EditText que herda a classe AppCompatEditText
 */
class MSPEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    /**
     * o bloco init runs toda vez que a classe é instanciada
     */
    init {
        // chama a func pra aplicar a fonte aos componentes
        applyFont()
    }

    /**
     * aplica a fonte ao EditText
     */
    private fun applyFont() {
        // This is used to get the file from the assets folder and set it to the title textView.
        //isso é usado pra pegar o file do folder de assets e set it no title textView
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}