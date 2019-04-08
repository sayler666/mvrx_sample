package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.sayler.mvrx.R
import kotlinx.android.synthetic.main.main_fragment.*

data class UserProfileState(val userId: String) : MvRxState

class UserProfileViewModel(initialState: UserProfileState) : MvRxViewModel<UserProfileState>(initialState) {
    companion object : MvRxViewModelFactory<UserProfileViewModel, UserProfileState> {
        override fun initialState(viewModelContext: ViewModelContext): UserProfileState? {
            val userId = (viewModelContext as FragmentViewModelContext).fragment<UserProfileFragment>().getUserId()
            return UserProfileState(userId)
        }
    }
}

class UserProfileFragment : BaseMvRxFragment() {

    companion object {
        private const val ARG_USER_ID = "user_id"
        fun newInstance(userId: String): UserProfileFragment =
            UserProfileFragment().apply { arguments = Bundle().apply { putString(ARG_USER_ID, userId) } }
    }

    private val viewModel: UserProfileViewModel by fragmentViewModel()

    fun getUserId() = arguments?.getString(ARG_USER_ID) ?: throw IllegalStateException("UserId missing!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun invalidate() = withState(viewModel) { state ->
        text.text = state.userId
    }

}
