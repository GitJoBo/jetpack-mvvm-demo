package com.jobo.uicommon.base

import android.view.View
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.jobo.commonmvvm.base.BaseDbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.getColorExt
import com.jobo.commonmvvm.ext.getDrawableExt
import com.jobo.uicommon.R

/**
 * @Desc: 使用ViewDataBinding 需要自定义修改什么就重写什么 具体方法可以 搜索 BaseIView 查看
 * 这里演示TitleBarView
 * @author: admin wsj
 * @Date: 2021/11/17 5:48 下午
 *
 */
abstract class UIDBBaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseDbActivity<VM, DB>() {
    lateinit var mToolbar: TitleBar

    override fun getTitleBarView(): View? {
        mToolbar = TitleBar(this)
        mToolbar.leftIcon = getDrawableExt(R.drawable.bar_arrows_left_white)
        mToolbar.titleView.setTextColor(getColorExt(R.color.white))
        mToolbar.leftView.setOnClickListener {
            finish()
        }
        return mToolbar
    }

    override fun initImmersionBar() {
        //设置共同沉浸式样式
        if (showToolBar()) {
            mToolbar.setBackgroundResource(R.color.colorPrimary)
            ImmersionBar.with(this).titleBar(mToolbar).init()
        }
    }
}