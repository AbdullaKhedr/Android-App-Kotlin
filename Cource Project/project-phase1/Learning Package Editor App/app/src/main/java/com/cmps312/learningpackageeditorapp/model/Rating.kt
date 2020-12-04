package com.cmps312.learningpackageeditorapp.model

import kotlinx.serialization.Serializable

@Serializable
class Rating(
    val comment: String = "",
    val doneAt: String = "",
    val doneBy: String = "",
    val rating: Double = 0.0
)