package com.cmps312.learningpackageeditorapp.model

import com.cmps312.learningpackageeditorapp.common.General
import kotlinx.serialization.Serializable

@Serializable
data class LearningPackage(
    var packageID: String = "",
    var authorName: String = "",
    var authorUserId: String = "",
    var category: String = "",
    var description: String = "",
    var media: String = "",
    var mediaType: General.ResourceTypeEnum = General.ResourceTypeEnum.NONE,
    var keyWords: List<String> = listOf(),
    var language: String = "",
    var lastUpdateDate: String = "",
    var level: String = "",
    var title: String = "",
    var version: Int = 0,
    var words: List<Word> = listOf()
    //val ratings: List<Rating>?,
)