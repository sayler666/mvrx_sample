package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.sayler.mvrx.R

class UserProfileFragment : BaseMvRxFragment() {
    companion object {
        private const val ARG_USER_ID = "user_id"
        fun newInstance(userId: String): UserProfileFragment =
            UserProfileFragment().apply { arguments = Bundle().apply { putString(ARG_USER_ID, userId) } }
    }

    fun getUserId() = arguments?.getString(ARG_USER_ID) ?: throw IllegalStateException("UserId missing!")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun invalidate() {

    }

}
