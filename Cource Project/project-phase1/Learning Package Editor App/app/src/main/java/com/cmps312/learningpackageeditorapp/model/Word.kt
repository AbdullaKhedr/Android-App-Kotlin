package com.cmps312.learningpackageeditorapp.model

import kotlinx.serialization.Serializable

@Serializable
class Word(
    var text: String = "",
    var definitions: List<Definition> = listOf(),
    var sentences: List<Sentence> = listOf(),
    var resources: List<Resource> = listOf()
)