package com.example.minhalojinha.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Uma classe data model para o User com os campos requeridos
 */

//isso nos permite passar complex objects as parcelable, serialized
@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0
): Parcelable