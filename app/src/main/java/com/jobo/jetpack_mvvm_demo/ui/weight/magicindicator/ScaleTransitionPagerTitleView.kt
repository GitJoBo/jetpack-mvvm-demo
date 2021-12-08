package com.jobo.jetpack_mvvm_demo.ui.weight.magicindicator

import android.content.Context
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * @Desc: 带颜色渐变和缩放的指示器标题
 * @author: admin wsj
 * @Date: 2021/12/8 11:34 上午
 *
 */
class ScaleTransitionPagerTitleView(context: Context?) : ColorTransitionPagerTitleView(context) {
    var minScale = 0.7f
    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        super.onEnter(index, totalCount, enterPercent, leftToRight)
        scaleX = minScale + (1.0f - minScale) * enterPercent
        scaleY = minScale + (1.0f - minScale) * enterPercent
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        super.onLeave(index, totalCount, leavePercent, leftToRight)
        scaleX = 1.0f + (minScale - 1.0f) * leavePercent
        scaleY = 1.0f + (minScale - 1.0f) * leavePercent
    }
}