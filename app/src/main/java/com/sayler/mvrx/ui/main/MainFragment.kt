package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.sayler.mvrx.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.concurrent.TimeUnit

data class State(
    val title: String = "Test",
    val temp: Async<Int> = Uninitialized
) : MvRxState

class HelloWorldViewModel(initialState: State) : MvRxViewModel<State>(initialState) {
    fun fetchTemp() {
        Observable.just(54)
            .delay(3, TimeUnit.SECONDS)
            .execute { copy(temp = it) }
    }
}

class MainFragment : BaseMvRxFragment() {
    companion object {
        private const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private val viewModel: HelloWorldViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text.setOnClickListener {
            viewModel.fetchTemp()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        text.text = when (state.temp) {
            is Uninitialized -> "Click to load weather"
            is Loading -> "Loading"
            is Success -> "Success ${state.temp}"
            is Fail -> "Error"
        }
    }
}
