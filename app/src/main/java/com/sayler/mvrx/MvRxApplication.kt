package com.sayler.mvrx

import android.app.Application
import com.sayler.mvrx.ui.main.UserRepository

class MvRxApplication : Application() {
    val userRepository = UserRepository()
}
