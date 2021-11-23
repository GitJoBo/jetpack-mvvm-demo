package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.jetpack_mvvm_demo.databinding.FragmentOneBinding
import com.jobo.uicommon.base.UIBaseFragment

class OneFragment: UIBaseFragment<BaseViewModel, FragmentOneBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(args: Bundle): OneFragment{
            val fragment = OneFragment()
            fragment.arguments = args
            return fragment
        }
    }
}