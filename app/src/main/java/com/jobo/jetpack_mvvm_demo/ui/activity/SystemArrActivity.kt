package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jobo.commonmvvm.base.BaseVbActivity
import com.jobo.commonmvvm.ext.logD
import com.jobo.commonmvvm.ext.logE
import com.jobo.commonmvvm.utils.Config
import com.jobo.commonmvvm.utils.log.KLog
import com.jobo.jetpack_mvvm_demo.app.ext.bindViewPager2
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.data.model.bean.SystemResponse
import com.jobo.jetpack_mvvm_demo.databinding.ActivitySystemArrBinding
import com.jobo.jetpack_mvvm_demo.ui.fragment.SystemChildFragment
import com.jobo.jetpack_mvvm_demo.viewModel.ThreeViewModel
import com.jobo.uicommon.base.UIBaseActivity
import kotlin.properties.Delegates

/**
 * @Desc: 体系-分类列表
 * @author: admin wsj
 * @Date: 2021/12/8 5:18 下午
 *
 */
class SystemArrActivity : UIBaseActivity<ThreeViewModel, ActivitySystemArrBinding>() {
    var mData: SystemResponse? = null
    var index = 0
    private val mFragments = arrayListOf<Fragment>()

    override fun initView(savedInstanceState: Bundle?) {
        mData = intent.getParcelableExtra<SystemResponse>(Config.KEY)
        index = intent.getIntExtra(Config.TYPE, 0)
        if (mData == null){
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
            mBind.includedMagicindicatorViewpager.viewPager2.currentItem = index
        }, 100)
        postDelayed.logD()
        mData?.children?.forEach {
            mFragments.add(SystemChildFragment.newInstance(it.id))
        }
    }


}