package com.jobo.jetpack_mvvm_demo.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.jobo.commonmvvm.base.BaseDbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.gone
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.commonmvvm.ext.visible
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivitySplashBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.SplashBannerAdapter
import com.jobo.uicommon.data.annotation.ValueKey
import com.jobo.uicommon.ext.mmkv
import com.zhpan.bannerview.BannerViewPager

/**
 * @Desc: 引导页
 * @author: admin wsj
 * @Date: 2021/11/19 10:41 上午
 *
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseDbActivity<BaseViewModel, ActivitySplashBinding>() {
    private val mResList = arrayOf(R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
    private lateinit var mViewPager:BannerViewPager<Int>

    override fun initView(savedInstanceState: Bundle?) {
        val isFirst = mmkv.getBoolean(ValueKey.isFirst,true)
        if (isFirst){
            mBind.splashImage.gone()
            mViewPager = mBind.splashBanner as BannerViewPager<Int>
            mViewPager.apply {
                adapter = SplashBannerAdapter()
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        if (position == mResList.size - 1) {
                            mBind.splashJoin.visible()
                        } else {
                            mBind.splashJoin.gone()
                        }
                    }
                })
                create(mResList.toList())
            }
        }else{
            mBind.splashImage.visible()
            mBind.splashBanner.gone()
            jumpToMainActivity()
        }

        mBind.splashJoin.setOnClickListener {
            mmkv.putBoolean(ValueKey.isFirst,false)
            jumpToMainActivity()
        }
    }

    private fun jumpToMainActivity(){
        toStartActivity(MainActivity::class.java)
        finish()
    }

    override fun showToolBar() = false
}