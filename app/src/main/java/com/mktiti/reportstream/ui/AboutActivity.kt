package com.mktiti.reportstream.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mktiti.reportstream.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        error("Crashlytics test error")

    }
}
