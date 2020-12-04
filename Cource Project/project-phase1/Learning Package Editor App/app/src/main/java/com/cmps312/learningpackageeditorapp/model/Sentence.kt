package com.cmps312.learningpackageeditorapp.model

import kotlinx.serialization.Serializable

@Serializable
class Sentence(
    var text: String = "",
    var resources: List<Resource> = listOf()
)