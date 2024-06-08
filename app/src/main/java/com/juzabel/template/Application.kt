package com.juzabel.template

import android.app.Application
import com.juzabel.local.di.localDi
import com.juzabel.network.di.networkDi
import com.juzabel.template.di.appDi
import com.juzabel.userlist.testDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(appDi, testDi, networkDi, localDi)
        }
    }
}
