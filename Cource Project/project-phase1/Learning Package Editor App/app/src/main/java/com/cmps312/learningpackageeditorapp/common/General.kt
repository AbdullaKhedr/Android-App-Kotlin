package com.cmps312.learningpackageeditorapp.common

import android.content.Context
import android.widget.Toast

object General {
    enum class ResourceTypeEnum {VIDEO, IMAGE, URL, NONE}

    fun Context?.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}