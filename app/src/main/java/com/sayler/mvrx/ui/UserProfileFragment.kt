package com.sayler.mvrx.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

data class UserProfileState(
    val userId: Long,
    val user: Async<User> = Uninitialized
) : MvRxState {
    constructor(args: UserProfileArgs) : this(userId = args.userId)
}

class UserProfileViewModel @AssistedInject constructor(
    @Assisted state: UserProfileState,
    private val usersService: UsersService
) : MvRxViewModel<UserProfileState>(state) {

    fun fetchUser() {
        withState {
            usersService
                .user(it.userId)
                .execute {
                    copy(user = it)
                }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: UserProfileState): UserProfileViewModel
    }

    companion object : MvRxViewModelFactory<UserProfileViewModel, UserProfileState> {
        override fun create(viewModelContext: ViewModelContext, state: UserProfileState): UserProfileViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<UserProfileFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}

@Parcelize
data class UserProfileArgs(val userId: Long) : Parcelable

class UserProfileFragment : BaseMvRxFragment() {

    companion object {
        fun newInstance(userId: Long): UserProfileFragment =
            UserProfileFragment().apply {
                arguments = Bundle().apply { putParcelable(MvRx.KEY_ARG, UserProfileArgs(userId)) }
            }
    }

    @Inject
    lateinit var viewModelFactory: UserProfileViewModel.Factory

    private val viewModel: UserProfileViewModel by fragmentViewModel()

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
        text.text = when (state.user) {
            Uninitialized -> "Empty"
            is Loading -> "Loading"
            is Success -> state.user.toString()
            is Fail -> "Error"
        }
    }
}
