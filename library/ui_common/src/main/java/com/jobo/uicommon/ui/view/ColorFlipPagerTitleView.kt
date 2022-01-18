package com.jobo.uicommon.ui.view

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/12/6 2:39 下午
 *
 */
class ColorFlipPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    private var mChangePercent = 0.5f

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor)
        } else {
            setTextColor(mSelectedColor)
        }
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor)
        } else {
            setTextColor(mNormalColor)
        }
    }

    override fun onSelected(index: Int, totalCount: Int) {}

    override fun onDeselected(index: Int, totalCount: Int) {}

    fun getChangePercent(): Float {
        return mChangePercent
    }

    fun setChangePercent(changePercent: Float) {
        mChangePercent = changePercent
    }
}