package com.mktiti.reportstream.presenter

import android.graphics.drawable.Drawable
import com.mktiti.reportstream.model.Language
import java.net.URI

data class ArticleItem(
    val author: String,
    val title: String,
    val description: String,
    val published: String?,
    val uri: URI,
    val image: Drawable?
)
