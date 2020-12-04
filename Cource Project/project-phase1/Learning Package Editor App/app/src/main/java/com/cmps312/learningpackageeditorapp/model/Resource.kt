package com.cmps312.learningpackageeditorapp.model

import com.cmps312.learningpackageeditorapp.common.General
import kotlinx.serialization.Serializable

@Serializable
class Resource(
    val extension: String = "",
    val resourceUrl: String = "",
    val title: String = "",
    val type: General.ResourceTypeEnum = General.ResourceTypeEnum.NONE
)