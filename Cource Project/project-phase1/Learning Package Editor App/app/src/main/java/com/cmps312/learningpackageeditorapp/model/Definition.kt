package com.cmps312.learningpackageeditorapp.model

import kotlinx.serialization.Serializable

@Serializable
class Definition(
    var text: String = "",
    var source: String = ""
)