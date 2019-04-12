package com.sayler.mvrx.ui.users

import com.airbnb.mvrx.*
import com.sayler.mvrx.MvRxViewModel
import com.sayler.mvrx.model.User
import com.sayler.mvrx.model.UsersService
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

data class UsersState(val users: Async<List<User>> = Uninitialized) : MvRxState

class UsersViewModel @AssistedInject constructor(
    @Assisted state: UsersState,
    private val usersService: UsersService
) : MvRxViewModel<UsersState>(state) {

    fun fetchUser() {
        usersService
            .users()
            .execute {
                copy(users = it)
            }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: UsersState): UsersViewModel
    }

    companion object : MvRxViewModelFactory<UsersViewModel, UsersState> {
        override fun create(viewModelContext: ViewModelContext, state: UsersState): UsersViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<UsersFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}
