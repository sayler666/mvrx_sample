package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.sayler.mvrx.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlin.random.Random

data class HelloWorldState(
    val title: String = "HelloWorld ${Random.nextInt(100)}"
) : MvRxState

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState)

class MainFragment : BaseMvRxFragment() {
    private val viewModel: HelloWorldViewModel by fragmentViewModel()

    companion object {

        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun invalidate() = withState(viewModel) {
        text.text = it.title
    }

}
