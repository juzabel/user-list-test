package com.juzabel.local.di

import com.juzabel.local.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDi = module {
    single { AppDatabase.create(androidApplication()) }
}
