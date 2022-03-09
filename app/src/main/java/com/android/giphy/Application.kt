package com.android.giphy

import android.app.Application
import com.android.giphy.di.DaggerApplicationComponent

class Application : Application() {
    val appComponent = DaggerApplicationComponent.create()
}