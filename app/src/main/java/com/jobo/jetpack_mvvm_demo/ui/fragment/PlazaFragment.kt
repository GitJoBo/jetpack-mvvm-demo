package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.logD
import com.jobo.jetpack_mvvm_demo.app.ext.bindViewPager2
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.databinding.FragmentSquareBinding

/**
 * @Desc: 广场  square,plaza
 * @author: admin wsj
 * @Date: 2021/12/8 10:07 上午
 *
 */
class PlazaFragment : BaseVbFragment<BaseViewModel, FragmentSquareBinding>() {
    val mTitleData = arrayListOf("广场", "每日一问", "体系", "导航")
    private var mFragments: ArrayList<Fragment> = arrayListOf()

    init {
        mFragments.add(PlazaChildFragment())
        mFragments.add(AskFragment())
        mFragments.add(SystemFragment())
        mFragments.add(NavigationFragment())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedMagicindicatorViewpager.viewPager2
            .init(this, mFragments, true)
            .offscreenPageLimit = mFragments.size
        mBind.includedMagicindicatorViewpager.magicIndicator
            .bindViewPager2(
                mBind.includedMagicindicatorViewpager.viewPager2,
                mTitleData
            ) {
                it.logD()
            }
//        ViewPagerHelper2.bind(mBind.includedMagicindicatorViewpager.magicIndicator, mBind.includedMagicindicatorViewpager.viewPager2)
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
//            titleBar(mBind.includedTitle.titleBar)
            titleBar(mBind.includedMagicindicatorViewpager.magicIndicator)
        }
    }
}