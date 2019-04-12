package com.sayler.mvrx.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.BaseMvRxActivity
import com.sayler.mvrx.R
import com.sayler.mvrx.ui.users.UsersFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseMvRxActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, UsersFragment())
//                .replace(R.id.container1, UserProfileFragment.newInstance(1))
                .commitNow()
        }
    }

}
