package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.viewModel.ThreeViewModel

class SystemChildFragment : BaseVbFragment<ThreeViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
//        fun newInstance(args: Bundle): SystemChildFragment {
//            val fragment = SystemChildFragment()
//            fragment.arguments = args
//            return fragment
//        }

        fun newInstance(cid: Int): SystemChildFragment {
            return SystemChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("cid", cid)
                }
            }
        }
    }
}