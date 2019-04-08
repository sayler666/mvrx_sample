package com.sayler.mvrx.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sayler.mvrx.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlin.random.Random

data class HelloWorldState(
    val title: String = "HelloWorld ${Random.nextInt(100)}"
)

class MainFragment : Fragment() {

    private val state = HelloWorldState()

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
        super.onViewCreated(view, savedInstanceState)
        text.text = state.title
    }

}
