package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hjq.bar.TitleBar
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.logD
import com.jobo.commonmvvm.ext.logE
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.app.ext.bindViewPager2
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.data.model.bean.SystemResponse
import com.jobo.jetpack_mvvm_demo.databinding.ActivitySystemArrBinding
import com.jobo.jetpack_mvvm_demo.ui.fragment.SystemChildFragment
import com.jobo.uicommon.base.UIBaseActivity

/**
 * @Desc: 体系-分类列表
 * @author: admin wsj
 * @Date: 2021/12/8 5:18 下午
 *
 */
class SystemArrActivity : UIBaseActivity<BaseViewModel, ActivitySystemArrBinding>() {
    var mData: SystemResponse? = null
    var index = 0
    private val mFragments = arrayListOf<Fragment>()

    override fun getTitleBarView(): View? {
        val titleBarView = super.getTitleBarView() as TitleBar
        titleBarView.setLineVisible(false)
        return titleBarView
    }

    override fun initView(savedInstanceState: Bundle?) {
        mData = intent.getParcelableExtra<SystemResponse>(Config.KEY)
        index = intent.getIntExtra(Config.TYPE, 0)
        if (mData == null) {
            "data is null".logE()
            return
        }
        mToolbar.run {
            title = mData?.name
        }
        mBind.includedMagicindicatorViewpager.viewPager2.init(this, mFragments)
        mBind.includedMagicindicatorViewpager.magicIndicator.bindViewPager2(
            mBind.includedMagicindicatorViewpager.viewPager2,
            mData?.children?.map { it.name }!!
        )
        val postDelayed = mBind.includedMagicindicatorViewpager.viewPager2.postDelayed({
            mBind.includedMagicindicatorViewpager.viewPager2.setCurrentItem(index, false)
        }, 100)
        postDelayed.logD()
        mData?.children?.forEach {
            mFragments.add(SystemChildFragment.newInstance(it.id))
        }
    }


}