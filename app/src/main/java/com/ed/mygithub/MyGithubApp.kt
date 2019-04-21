package com.ed.mygithub

import android.app.Application
import timber.log.Timber

class MyGithubApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? =
                    super.createStackElementTag(element) + ":" + element.lineNumber
            }
            )
        }
    }
}