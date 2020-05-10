package com.mktiti.reportstream.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.mktiti.reportstream.R
import java.net.URI

class ArticleActivity : AppCompatActivity() {

    private lateinit var address: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        setSupportActionBar(findViewById(R.id.article_toolbar))

        val view = findViewById<WebView>(R.id.article_content_View)

        val addressString: String = intent.getStringExtra("address")!!
        address = addressString.toUri()
        view.loadUrl(addressString)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.article_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_open_article -> {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = address
            }
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
