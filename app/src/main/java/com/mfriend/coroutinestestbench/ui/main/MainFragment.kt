package com.mfriend.coroutinestestbench.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.mfriend.coroutinestestbench.R
import kotlinx.android.synthetic.main.main_fragment.btn_launch_dont_extend
import kotlinx.android.synthetic.main.main_fragment.btn_launch_extend
import kotlinx.android.synthetic.main.main_fragment.btn_launch_extend_timeout
import kotlinx.android.synthetic.main.main_fragment.tv_result

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_launch_dont_extend.setOnClickListener {
            tv_result.text = ""
            viewModel.launchDontExtend()
        }
        btn_launch_extend.setOnClickListener {
            tv_result.text = ""
            viewModel.launchExtend()
        }
        btn_launch_extend_timeout.setOnClickListener {
            tv_result.text = ""
            viewModel.launchExtendThenTimeout()
        }

        viewModel.message.observe(this) {
            tv_result.text = it
        }
    }

}
