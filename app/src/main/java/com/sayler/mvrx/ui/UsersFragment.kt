package com.sayler.mvrx.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.sayler.mvrx.MvRxViewModel
import com.sayler.mvrx.R
import com.sayler.mvrx.model.User
import com.sayler.mvrx.model.UsersService
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

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

class UsersFragment : BaseMvRxFragment() {

    @Inject
    lateinit var viewModelFactory: UsersViewModel.Factory
    private val viewModel: UsersViewModel by fragmentViewModel()

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        text.setOnClickListener {
            viewModel.fetchUser()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        text.text = when (state.users) {
            Uninitialized -> "Empty"
            is Loading -> "Loading"
            is Success -> state.users.toString()
            is Fail -> "Error"
        }
    }
}
