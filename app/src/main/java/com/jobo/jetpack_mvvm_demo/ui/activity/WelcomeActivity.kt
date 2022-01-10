package com.jobo.jetpack_mvvm_demo.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.jobo.commonmvvm.base.BaseVbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.annotation.ValueKey
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.WelcomeBean
import com.jobo.jetpack_mvvm_demo.databinding.ActivityWelcomeBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.SimpleAdapter
import com.jobo.jetpack_mvvm_demo.ui.transform.PageTransformerFactory
import com.jobo.jetpack_mvvm_demo.ui.transform.TransformerStyle
import com.jobo.uicommon.ext.mmkv
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.enums.IndicatorSlideMode
import java.util.*

class WelcomeActivity : BaseVbActivity<BaseViewModel, ActivityWelcomeBinding>() {
    private lateinit var mViewPager: BannerViewPager<WelcomeBean>
    private val des = arrayOf("在这里\n你可以听到周围人的心声", "在这里\nTA会在下一秒遇见你", "在这里\n不再错过可以改变你一生的人")
    protected var mDrawableList: MutableList<Int> = ArrayList()

    private fun initData() {
        for (i in 0..2) {
            val drawable = resources.getIdentifier("guide$i", "drawable", packageName)
            mDrawableList.add(drawable)
        }
    }

    private val transforms = intArrayOf(
        TransformerStyle.NONE,
        TransformerStyle.ACCORDION,
        TransformerStyle.DEPTH,
        TransformerStyle.DEPTH_SCALE,
        TransformerStyle.ROTATE,
        TransformerStyle.SCALE_IN,
        TransformerStyle.ROTATE_UP
    )

    private val data: List<WelcomeBean>
        get() {
            val list = ArrayList<WelcomeBean>()
            for (i in mDrawableList.indices) {
                val customBean = WelcomeBean()
                customBean.imageRes = mDrawableList[i]
                customBean.imageDescription = des[i]
                list.add(customBean)
            }
            return list
        }

    override fun initView(savedInstanceState: Bundle?) {
        val isFirst = mmkv.getBoolean(ValueKey.isFirst, true)
        if (isFirst) {
            initData()
            setupViewPager()
        } else {
            jumpToMainActivity()
        }

    }

    private fun setupViewPager() {
        mViewPager = mBind.viewpager as BannerViewPager<WelcomeBean>
//        val simpleAdapter = SimpleAdapter()
        mViewPager.apply {
            setCanLoop(false)
            setPageTransformer(
                PageTransformerFactory.createPageTransformer(transforms[Random().nextInt(7)])
            )
            setIndicatorMargin(
                0, 0, 0, resources.getDimension(R.dimen.dp_100)
                    .toInt()
            )
            setIndicatorSliderGap(
                resources.getDimension(R.dimen.dp_10)
                    .toInt()
            )
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorSliderRadius(
                resources.getDimension(R.dimen.dp_3)
                    .toInt(), resources.getDimension(R.dimen.dp_5)
                    .toInt()
            )
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    BannerUtils.log("position:$position")
                    updateUI(position)
                }
            })
//            adapter = simpleAdapter
            adapter = SimpleAdapter().apply {
                mOnSubViewClickListener = object : SimpleAdapter.OnSubViewClickListener {
                    override fun onViewClick(
                        view: View?,
                        position: Int,
                    ) {
                        ToastUtils.showShort("Logo Clicked,position:$position")
                    }
                }
            }
            setIndicatorSliderColor(
                ContextCompat.getColor(this@WelcomeActivity, R.color.white),
                ContextCompat.getColor(this@WelcomeActivity, R.color.white_alpha_75)
            )
        }
            .create(data)
    }

    private fun updateUI(position: Int) {
        mBind.tvDescribe.text = des[position]
        val translationAnim = ObjectAnimator.ofFloat(mBind.tvDescribe, "translationX", -120f, 0f)
        translationAnim.apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = DecelerateInterpolator()
        }
        val alphaAnimator = ObjectAnimator.ofFloat(mBind.tvDescribe, "alpha", 0f, 1f)
        alphaAnimator.apply {
            duration = ANIMATION_DURATION.toLong()
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(translationAnim, alphaAnimator)
        animatorSet.start()

        if (position == mBind.viewpager.data.size - 1 && mBind.btnStart.visibility == View.GONE) {
            mBind.btnStart.visibility = View.VISIBLE
            ObjectAnimator
                .ofFloat(mBind.btnStart, "alpha", 0f, 1f)
                .setDuration(ANIMATION_DURATION.toLong())
                .start()
        } else {
            mBind.btnStart.visibility = View.GONE
        }
    }

    fun onClick(view: View) {
        mmkv.putBoolean(ValueKey.isFirst,false)
        jumpToMainActivity()
    }

    private fun jumpToMainActivity() {
        toStartActivity<MainActivity>(this)
        finish()
    }

    companion object {
        private const val ANIMATION_DURATION = 1300
        private const val MIN_SCALE = 0.9f
        private const val MIN_ALPHA = 0.7f
    }
}