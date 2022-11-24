package com.example.minhalojinha.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.minhalojinha.R
import java.io.IOException

/**
 * um objeto custom para criar uma common funcrions para o Glide que podem ser usaas na aplicacao inteira
 */
class GlideLoader(val context: Context) {

    /**
     * uma funcao para load image from URI ou URL para a profile picture do usuario
     */
    fun loadUserPicture(image: Any, imageView: ImageView) {
        try {
            // carrega a image no imageview
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .placeholder(R.drawable.ic_user_placeholder) // A default place holder if image is failed to load.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * uma funcao para load image da URI ou URL para a imagem do livro
     */
    fun loadProductPicture(image: Any, imageView: ImageView) {
        try {
            // carrega a image no imageview
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}