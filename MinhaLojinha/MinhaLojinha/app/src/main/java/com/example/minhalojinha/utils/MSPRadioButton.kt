package com.example.minhalojinha.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

/**
 * essa classa sera usada para custom font text usando o Radio Button que herda o AppCompatRdioButton class
 */
class MSPRadioButton(context: Context, attrs: AttributeSet) :
    AppCompatRadioButton(context, attrs) {

    /**
     * o bloco init runs toda vez que a classe é instanciada
     */
    init {
        // Call the function to apply the font to the components.
        applyFont()
    }

    /**
     * aplica a fonte ao radio button
     */
    private fun applyFont() {

        // This is used to get the file from the assets folder and set it to the title textView.
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}