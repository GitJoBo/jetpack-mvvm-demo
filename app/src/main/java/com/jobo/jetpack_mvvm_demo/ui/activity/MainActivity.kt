package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import com.jobo.commonmvvm.base.BaseDbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.finishAllNotTop
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.initMain
import com.jobo.jetpack_mvvm_demo.databinding.ActivityMainBinding

class MainActivity : BaseDbActivity<BaseViewModel, ActivityMainBinding>() {
    override fun showToolBar(): Boolean {
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {
        finishAllNotTop()
//        mBind.mainViewPager2.adapter = MainAdapter(this)
//        mBind.mainViewPager2.offscreenPageLimit = mBind.mainViewPager2.adapter!!.itemCount
//        mBind.mainViewPager2.isUserInputEnabled = false
        mBind.mainViewPager2.initMain(this)
        mBind.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationHome -> {
                    mBind.mainViewPager2.setCurrentItem(0, false)
                }
                R.id.navigationProject -> {
                    mBind.mainViewPager2.setCurrentItem(1, false)
                }
                R.id.navigationSquare -> {
                    mBind.mainViewPager2.setCurrentItem(2, false)
                }
                R.id.navigationPublic -> {
                    mBind.mainViewPager2.setCurrentItem(3, false)
                }
                R.id.navigationMine -> {
                    mBind.mainViewPager2.setCurrentItem(4, false)
                }
            }
            true
        }

        test()
    }

    private fun test() {

    }

}