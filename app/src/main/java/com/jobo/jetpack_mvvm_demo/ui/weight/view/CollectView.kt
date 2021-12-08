package com.jobo.jetpack_mvvm_demo.ui.weight.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.VibrateUtils
import com.jobo.commonmvvm.ext.logD
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.ui.activity.LoginActivity
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil
import per.goweii.reveallayout.RevealLayout

/**
 * @Desc: 关注/取消关注
 * @author: admin wsj
 * @Date: 2021/12/2 9:35 上午
 *
 */
class CollectView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RevealLayout(context, attrs, defStyleAttr),
    View.OnTouchListener {

    private var onCollectViewClickListener: OnCollectViewClickListener? = null

    override fun initAttr(attrs: AttributeSet) {
        super.initAttr(attrs)
        setCheckWithExpand(true)
        setUncheckWithExpand(false)
        setCheckedLayoutId(R.layout.layout_collect_view_checked)
        setUncheckedLayoutId(R.layout.layout_collect_view_unchecked)
        setAnimDuration(300)
        setAllowRevert(true)
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                //震动一下
                VibrateUtils.vibrate(40)
                if (CacheUtil.isLogin()) {
                    onCollectViewClickListener?.onClick(this)
                } else {
                    isChecked = true
                    //TODO 去登录
                    "去登录".logD()
                    toStartActivity<LoginActivity>()
                }

            }
        }
        return false
    }

    fun setOnCollectViewClickListener(onCollectViewClickListener: OnCollectViewClickListener) {
        this.onCollectViewClickListener = onCollectViewClickListener
    }

    interface OnCollectViewClickListener {
        fun onClick(v: CollectView)
    }
}