package com.sayler.mvrx

import android.os.Bundle
import com.airbnb.mvrx.BaseMvRxActivity
import com.sayler.mvrx.ui.main.InitFromArgFragment

class MainActivity : BaseMvRxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, InitFromArgFragment.newInstance("42"))
                .commitNow()
        }
    }

}
