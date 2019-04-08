package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.sayler.mvrx.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

data class HelloWorldState(
    val title: String = "HelloWorld",
    val temperature: Async<Int> = Uninitialized
) : MvRxState

class HelloWorldViewModel(initialState: HelloWorldState) : MvRxViewModel<HelloWorldState>(initialState) {
    fun fetchTemp() {
        Observable.just(Random.nextInt(40))
            .delay(3, TimeUnit.SECONDS)
            .execute { copy(temperature = it) }
    }
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
            viewModel.fetchTemp()
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        text.text = when (state.temperature) {
            is Uninitialized -> "Click to load"
            is Loading -> "Loading..."
            is Success -> "Temperature: ${state.temperature()}"
            is Fail -> "Error: ${state.temperature.error.message}"
        }
    }

}
