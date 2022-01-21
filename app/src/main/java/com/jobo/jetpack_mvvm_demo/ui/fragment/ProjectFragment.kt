package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ktx.immersionBar
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.getColorExt
import com.jobo.commonmvvm.ext.logD
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.init
import com.jobo.jetpack_mvvm_demo.databinding.FragmentProjectBinding
import com.jobo.jetpack_mvvm_demo.viewModel.ProjectViewModel
import com.jobo.uicommon.ui.view.ColorFlipPagerTitleView
import com.jobo.uicommon.ui.view.ViewPagerHelper2
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * @Desc: 项目
 * @author: admin wsj
 * @Date: 2021/12/6 2:18 下午
 *
 */
class ProjectFragment : BaseVbFragment<ProjectViewModel, FragmentProjectBinding>() {
    //fragment集合
    var mFragments: ArrayList<Fragment> = arrayListOf()

    //标题集合
    var mDataList: ArrayList<String> = arrayListOf()

    override fun initView(savedInstanceState: Bundle?) {
//        mBind.includedTitle.titleBar.title = "项目"
//        mBind.includedTitle.titleBar.titleView.setTextColor(getColorExt(R.color.white))
//        mBind.includedTitle.titleBar.leftView.visibility = View.GONE
        mBind.viewPager2.init(this, mFragments, true)
        mBind.magicIndicator.setBackgroundColor(getColorExt(R.color.colorAccent))
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.run {
            scrollPivotX = 0.65f
            adapter = object : CommonNavigatorAdapter() {
                override fun getCount(): Int {
                    return mDataList.size
                }

                override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                    val colorFlipPagerTitleView = ColorFlipPagerTitleView(requireContext())
                    colorFlipPagerTitleView.run {
                        text = mDataList[index]
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
        mViewModel.getProjectTree()
    }

    override fun onRequestSuccess() {
        mViewModel.mProjectTree.observe(viewLifecycleOwner, {
            mDataList.clear()
            mFragments.clear()
            mDataList.add("最新项目")
            mDataList.addAll(it.map { it.name })
            mFragments.add(ProjectChildFragment.newInstance(0, true))
            it.forEach { classify ->
                mFragments.add(ProjectChildFragment.newInstance(classify.id, false))
            }
            mBind.magicIndicator.navigator.notifyDataSetChanged()
            mBind.viewPager2.offscreenPageLimit = mFragments.size

        })
    }


    override fun onResume() {
        super.onResume()
        immersionBar {
//            titleBar(mBind.includedTitle.titleBar)
            titleBar(mBind.magicIndicator)
        }
    }

    override fun getLoadingView(): View? {
        return mBind.viewPager2
    }


}