package com.sayler.mvrx.ui.users

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.sayler.mvrx.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

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
