package com.sayler.mvrx

import com.sayler.mvrx.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MvRxApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent
            .builder()
            .applicationContext(this)
            .build()
            .also {
                it.inject(this)
            }
}
