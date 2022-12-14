package com.example.minhalojinha.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class p produto
 */
@Parcelize
data class Product(
    val user_id: String = "",
    val user_name: String = "",
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val nota: String = "",
    val image: String = "",
    var product_id: String = "",
) : Parcelable