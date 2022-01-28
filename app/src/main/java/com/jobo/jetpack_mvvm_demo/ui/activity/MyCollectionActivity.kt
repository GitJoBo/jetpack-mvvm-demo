package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.logD
import com.jobo.jetpack_mvvm_demo.app.ext.bindViewPager2
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.databinding.ActivityMyCollectionBinding
import com.jobo.jetpack_mvvm_demo.ui.fragment.CollectArticleFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.CollectUrlFragment
import com.jobo.uicommon.R
import com.jobo.uicommon.base.UIVBBaseActivity

/**
 * @Desc: 我的收藏
 * @author: admin wsj
 * @Date: 2022/1/26 2:02 下午
 *
 */
class MyCollectionActivity : UIVBBaseActivity<BaseViewModel, ActivityMyCollectionBinding>() {
    private val mTitles = arrayListOf("文章", "网址")
    private val mFragments: ArrayList<Fragment> by lazy {
        arrayListOf(CollectArticleFragment(), CollectUrlFragment())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewPager2.init(this@MyCollectionActivity, mFragments)
            .offscreenPageLimit = mFragments.size

        mBind.magicIndicator.let {
            it.bindViewPager2(mBind.viewPager2, mTitles) { t ->
                t.logD()
            }
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun initImmersionBar() {
        mBind.magicIndicator.setBackgroundResource(R.color.colorPrimary)
        mBind.llTitle.setBackgroundResource(R.color.colorPrimary)
        ImmersionBar.with(this).titleBar(mBind.magicIndicator).init()
    }
}