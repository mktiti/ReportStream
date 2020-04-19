package com.mktiti.reportstream

import android.app.Application
import com.mktiti.reportstream.db.DbModule
import com.mktiti.reportstream.network.NetModule

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .dbModule(DbModule(this))
            .netModule(NetModule())
            .build()
    }

}