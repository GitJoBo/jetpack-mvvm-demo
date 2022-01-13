package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.FragmentMeBinding
import com.jobo.jetpack_mvvm_demo.ui.weight.XCollapsingToolbarLayout
import com.jobo.jetpack_mvvm_demo.viewModel.MeViewModel
import com.jobo.uicommon.base.UIVBBaseFragment

/**
 * @Desc: 我的
 * @author: admin wsj
 * @Date: 2022/1/11 2:42 下午
 *
 */
class MeFragment : UIVBBaseFragment<MeViewModel, FragmentMeBinding>(), XCollapsingToolbarLayout.OnScrimsListener {
    override fun initView(savedInstanceState: Bundle?) {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(requireActivity(), mBind.tbHomeTitle)
        mBind.xcltlMe.setScrimsListener(this)
    }

    override fun onScrimsStateChange(layout: XCollapsingToolbarLayout?, shown: Boolean) {
//        ImmersionBar.with(this).statusBarDarkFont(shown).init()
        mBind.tvTitle?.setTextColor(ContextCompat.getColor(requireActivity(), if (shown) R.color.black else R.color.white))
    }
}