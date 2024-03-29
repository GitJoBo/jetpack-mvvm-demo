package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar
import com.jobo.commonmvvm.ext.getColorExt
import com.jobo.commonmvvm.ext.logD
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.databinding.IncludeMagicindicatorViewpagerBinding
import com.jobo.jetpack_mvvm_demo.viewModel.PublicViewModel
import com.jobo.uicommon.base.UIVBBaseFragment
import com.jobo.uicommon.ui.view.ColorFlipPagerTitleView
import com.jobo.uicommon.ui.view.ViewPagerHelper2
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @Desc: 公众号
 * @author: admin wsj
 * @Date: 2022/1/11 9:28 上午
 *
 */
class PublicFragment : UIVBBaseFragment<PublicViewModel, IncludeMagicindicatorViewpagerBinding>() {

    private val mFragments: ArrayList<Fragment> = arrayListOf()
    private val mTitles: ArrayList<String> = arrayListOf()

    override fun initView(savedInstanceState: Bundle?) {
        mBind.viewPager2.init(this, mFragments)
        mBind.magicIndicator.setBackgroundColor(getColorExt(R.color.colorAccent))
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.run {
            scrollPivotX = 0.65f
            adapter = object : CommonNavigatorAdapter() {
                override fun getCount(): Int {
                    return mTitles.size
                }

                override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                    val colorFlipPagerTitleView = ColorFlipPagerTitleView(requireContext())
                    colorFlipPagerTitleView.run {
                        text = mTitles[index]
                        normalColor = getColorExt(R.color.black)
                        selectedColor = getColorExt(R.color.white)
                        setOnClickListener {
                            "选择了 $text".logD()
                            mBind.viewPager2.currentItem = index
                        }
                    }
                    return colorFlipPagerTitleView
                }

                override fun getIndicator(context: Context?): IPagerIndicator {
                    val indicator = LinePagerIndicator(context)
                    indicator.mode = LinePagerIndicator.MODE_EXACTLY
                    indicator.lineHeight = UIUtil.dip2px(context, 6.0).toFloat()
                    indicator.lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
                    indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                    indicator.startInterpolator = AccelerateInterpolator()
                    indicator.endInterpolator = DecelerateInterpolator(2.0f)
                    indicator.setColors(getColorExt(R.color.white))
                    return indicator
                }

            }
        }
        mBind.magicIndicator.navigator = commonNavigator
        ViewPagerHelper2.bind(mBind.magicIndicator, mBind.viewPager2)
    }

    override fun lazyLoadData() {
        mViewModel.getWxArticle()
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
//            titleBar(mBind.includedTitle.titleBar)
            titleBar(mBind.magicIndicator)
        }
    }

    override fun onRequestSuccess() {
        mViewModel.mWxArticle.observe(viewLifecycleOwner, {
            mTitles.clear()
            mFragments.clear()
//            mTitles.addAll(it.map { it.name })
            it.forEach {
                mTitles.add(it.name)
                mFragments.add(PublicChildFragment.newInstance(it.id))
            }
            mBind.magicIndicator.navigator.notifyDataSetChanged()
            mBind.viewPager2.offscreenPageLimit = mFragments.size
        })
    }

    override fun getLoadingView(): View? {
        return mBind.viewPager2
    }

}