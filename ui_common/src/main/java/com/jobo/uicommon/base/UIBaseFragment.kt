package com.jobo.uicommon.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjq.bar.TitleBar
import com.jobo.commonmvvm.base.BaseDbFragment
import com.jobo.commonmvvm.base.BaseViewModel

/**
 * @Desc: 使用ViewDataBinding 需要自定义修改什么就重写什么 具体方法可以 搜索 BaseIView 查看
 * 这里演示TitleBarView
 * @author: admin wsj
 * @Date: 2021/11/17 5:48 下午
 *
 */
abstract class UIBaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseDbFragment<VM, DB>() {
    lateinit var mToolbar: TitleBar

    override fun getTitleBarView(): View? {
        mToolbar = TitleBar(requireContext())
        return mToolbar
    }

}