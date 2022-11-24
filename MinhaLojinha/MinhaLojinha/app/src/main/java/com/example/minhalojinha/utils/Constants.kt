package com.example.minhalojinha.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

/**
 * um objeto para declarar todas os valores constantes em um unico file. os valores constantes declarados aqyi podem ser usados na apkicacao inteira
 */
object Constants {

    // Constantes da Firebase
    //Isso é usado para a collection name p USERS
    const val USERS: String = "users"
    const val PRODUCTS: String = "products"

    const val MINHALOJINHA_PREFERENCES: String = "MINHALOJINHAPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    // Intent extra constants.
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val EXTRA_PRODUCT_ID: String = "extra_product_id"
    const val EXTRA_PRODUCT_OWNER_ID: String = "extra_product_owner_id"

    //um codigo unico p pedir Read Storage Permission, usando isso nos seremos capazes de checar and identificar no metodo onRequestPermissionResult na Base Activity
    const val READ_STORAGE_PERMISSION_CODE = 2

    // A unique code of image selection from Phone Storage.
    //um codigo unico de image selection do phone storage
    const val PICK_IMAGE_REQUEST_CODE = 2
    const val ADD_ADDRESS_REQUEST_CODE: Int = 121

    // Constant variables for Gender
    const val MALE: String = "male"
    const val FEMALE: String = "female"

    const val DEFAULT_CART_QUANTITY: String = "1"

    // Firebase database field names
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE: String = "profileCompleted"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val USER_ID: String = "user_id"
    const val PRODUCT_ID: String = "product_id"

    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val PRODUCT_IMAGE: String = "Product_Image"


    const val NOTA: String = "nota"


    /**
     * Uma funcao pra selecionar user profile image da storage do telefone
     */
    fun showImageChooser(activity: Activity) {
        // An intent for launching the image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    /**
     * uma funcao pra pegar o file extension da imagem selecionada
     * @param activity Activity reference.
     * @param uri Image file uri.
     */
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}