package com.jobo.uicommon.ui.view

import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator

/**
 * @Desc: MagicIndicator + ViewPager辅助类
 * @author: admin wsj
 * @Date: 2021/12/6 2:59 下午
 *
 */
class ViewPagerHelper2 {
    companion object {
        fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager2) {
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    magicIndicator.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    magicIndicator.onPageScrollStateChanged(state)
                }
            })
        }

        fun bind(magicIndicator: MagicIndicator, viewPager: ViewPager) {
            viewPager.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    magicIndicator.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    magicIndicator.onPageScrollStateChanged(state)
                }
            })
        }
    }

}