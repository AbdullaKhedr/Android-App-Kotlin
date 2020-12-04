package com.cmps312.learningpackageeditorapp.model

import kotlinx.serialization.Serializable

@Serializable
class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val role: String = ""
)