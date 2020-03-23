package com.mktiti.reportstream.model

import java.net.URI
import java.time.LocalDateTime

data class Language(
    val name: String,
    val code: String
)

sealed class LanguageFilter {

    object All : LanguageFilter()

    data class Selected(val allowed: List<Language>) : LanguageFilter()

}

data class Article(
    val author: String,
    val title: String,
    val description: String,
    val published: LocalDateTime?,
    val imageUrl: URI?
)
