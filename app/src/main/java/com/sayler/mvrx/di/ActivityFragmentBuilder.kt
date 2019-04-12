package com.sayler.mvrx.di

import com.sayler.mvrx.ui.MainActivity
import com.sayler.mvrx.ui.userprofile.UserProfileFragment
import com.sayler.mvrx.ui.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun bindUserProfileFragment(): UserProfileFragment
}
