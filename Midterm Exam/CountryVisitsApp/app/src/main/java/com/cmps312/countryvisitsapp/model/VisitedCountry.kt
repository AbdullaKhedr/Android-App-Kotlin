package com.cmps312.countryvisitsapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class VisitedCountry(
    val code: String,
    val name: String,
    val rating: Float,
    val amount: Float
) : Parcelable