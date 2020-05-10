package com.mktiti.reportstream.presenter

import java.net.URI

data class ArticleItem(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val published: String?,
    val uri: URI,
    val image: String?
)
