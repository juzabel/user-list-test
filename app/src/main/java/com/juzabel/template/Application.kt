package com.juzabel.template

import android.app.Application
import com.juzabel.local.di.localDi
import com.juzabel.network.di.networkDi
import com.juzabel.testapp.testDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(testDi, networkDi, localDi)
        }
    }
}
