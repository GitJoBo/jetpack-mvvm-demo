package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import com.jobo.jetpack_mvvm_demo.databinding.FragmentSearchBinding
import com.jobo.jetpack_mvvm_demo.viewModel.SearchViewModel
import com.jobo.uicommon.base.UIVBBaseFragment

/**
 * @Desc: 搜索
 * @author: admin wsj
 * @Date: 2022/1/20 9:43 上午
 *
 */
class SearchFragment : UIVBBaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mToolbar?.title = "搜索"
    }
}