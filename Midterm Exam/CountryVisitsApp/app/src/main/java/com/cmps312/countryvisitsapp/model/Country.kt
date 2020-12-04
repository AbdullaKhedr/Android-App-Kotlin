package com.cmps312.countryvisitsapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class Country(
    val code: String,
    val name: String
) : Parcelable