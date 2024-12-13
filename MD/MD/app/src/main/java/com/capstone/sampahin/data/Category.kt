package com.capstone.sampahin.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category (
    var name: String,
    var image: String
) : Parcelable