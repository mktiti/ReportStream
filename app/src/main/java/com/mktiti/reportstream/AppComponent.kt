package com.mktiti.reportstream

import com.mktiti.reportstream.db.DbModule
import com.mktiti.reportstream.network.NetModule
import com.mktiti.reportstream.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, DbModule::class])
interface AppComponent {

    fun inject(myApplication: MainActivity)

}
