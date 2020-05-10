package com.mktiti.reportstream.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.mktiti.reportstream.R

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val view = findViewById<WebView>(R.id.article_content_View)

        val address = intent.getStringExtra("address")
        view.loadUrl(address)

    }
}
