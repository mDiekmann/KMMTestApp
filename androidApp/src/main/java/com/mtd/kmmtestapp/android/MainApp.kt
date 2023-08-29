package com.mtd.kmmtestapp.android

import android.app.Application
import com.mtd.kmmtestapp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@MainApp)
        }
    }
}