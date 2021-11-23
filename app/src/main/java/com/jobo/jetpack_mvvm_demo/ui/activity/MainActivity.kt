package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import com.jobo.commonmvvm.base.BaseDbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivityMainBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.MainAdapter

class MainActivity : BaseDbActivity<BaseViewModel, ActivityMainBinding>() {
    override fun showToolBar(): Boolean {
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.mainViewPager2.adapter = MainAdapter(this)
        mBind.mainViewPager2.offscreenPageLimit = mBind.mainViewPager2.adapter!!.itemCount
        mBind.mainViewPager2.isUserInputEnabled = false
        mBind.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationOne -> {
                    mBind.mainViewPager2.setCurrentItem(0, false)
                }
                R.id.navigationTwo -> {
                    mBind.mainViewPager2.setCurrentItem(1, false)
                }
                R.id.navigationThree -> {
                    mBind.mainViewPager2.setCurrentItem(2, false)
                }
                R.id.navigationFour -> {
                    mBind.mainViewPager2.setCurrentItem(3, false)
                }
            }
            true
        }
    }

}