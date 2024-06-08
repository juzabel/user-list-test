package com.juzabel.template.di

import com.juzabel.template.R
import com.juzabel.util.config.CommonConfig
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appDi =
    module {
        single<CommonConfig> {
            CommonConfig(url = androidApplication().resources.getString(R.string.base_url))
        }
    }
