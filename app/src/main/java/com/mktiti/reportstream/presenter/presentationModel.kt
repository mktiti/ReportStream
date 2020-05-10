package com.mktiti.reportstream.presenter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import java.net.URI

data class LanguageOption(
    val name: String,
    val flag: String?
)

data class ArticleItem(
    val author: String,
    val title: String,
    val description: String,
    val published: String?,
    val uri: URI,
    val image: Drawable?
)
