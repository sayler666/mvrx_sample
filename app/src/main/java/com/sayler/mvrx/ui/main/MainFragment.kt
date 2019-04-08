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

data class HelloWorldState(
    val title: String = "HelloWorld",
    val count: Int = 0
) : MvRxState {
    var titleWithCount = "$title $count"
}

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {
    fun incrementCount() = setState { copy(count = count + 1) }
}

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        text.setOnClickListener {
            viewModel.incrementCount()
        }
    }

    override fun invalidate() = withState(viewModel) {
        text.text = it.titleWithCount
    }

}
