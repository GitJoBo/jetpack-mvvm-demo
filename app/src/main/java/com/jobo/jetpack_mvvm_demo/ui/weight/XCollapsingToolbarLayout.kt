package com.jobo.jetpack_mvvm_demo.ui.weight

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.CollapsingToolbarLayout

/**
 * @Desc: 支持监听渐变的 CollapsingToolbarLayout
 * @author: admin wsj
 * @Date: 2022/1/13 9:37 上午
 *
 */
class XCollapsingToolbarLayout @JvmOverloads constructor
    (context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    CollapsingToolbarLayout(context, attrs, defStyleAttr) {
    /**
     * 渐变监听
     */
    private var mScrimsListener: OnScrimsListener? = null

    /**
     * 当前渐变状态
     */
    private var mScrimsShownStatus: Boolean = false

    override fun setScrimsShown(shown: Boolean, animate: Boolean) {
        super.setScrimsShown(shown, animate)
        // 判断渐变状态是否改变了
        if (mScrimsShownStatus == shown) {
            return
        }
        // 如果是就记录并且回调监听器
        mScrimsShownStatus = shown
        mScrimsListener?.onScrimsStateChange(this, mScrimsShownStatus)
    }

    /**
     * 获取当前渐变状态
     */
    fun isScrimsShownStatus(): Boolean {
        return mScrimsShownStatus
    }

    /**
     * 设置渐变监听
     */
    fun setScrimsListener(listener: OnScrimsListener) {
        mScrimsListener = listener;
    }

    /**
     * CollapsingToolbarLayout渐变监听器
     */
    interface OnScrimsListener {
        /**
         * 渐变状态变化
         *
         * @param shown         渐变开关
         */
        fun onScrimsStateChange(layout: XCollapsingToolbarLayout?, shown: Boolean)
    }
}