package com.jobo.jetpack_mvvm_demo.app.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jobo.commonmvvm.base.appContext
import com.jobo.commonmvvm.ext.getColorExt
import com.jobo.commonmvvm.ext.logD
import com.jobo.commonmvvm.ext.toHtml
import com.jobo.commonmvvm.utils.log.KLog
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.ui.fragment.FourFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.OneFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.ThreeFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.TwoFragment
import com.jobo.jetpack_mvvm_demo.ui.weight.magicindicator.ScaleTransitionPagerTitleView
import com.jobo.uicommon.ui.view.ViewPagerHelper2
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @Desc: 一些自定义扩展
 * @author: admin wsj
 * @Date: 2021/12/6 3:17 下午
 *
 */
/**
 * 主页面条目
 * @receiver ViewPager2
 */
fun ViewPager2.initMain(activity: FragmentActivity): ViewPager2 {
    isUserInputEnabled = false
    offscreenPageLimit = 4
    adapter = object : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    OneFragment()
                }
                1 -> {
                    TwoFragment()
                }
                2 -> {
                    ThreeFragment()
                }
                else -> {
                    FourFragment()
                }
            }
        }

        override fun getItemCount(): Int {
            return 4
        }
    }
    return this
}

/**
 * 初始化ViewPager2
 * @receiver ViewPager2
 * @param fragment Fragment
 * @param fragments ArrayList<Fragment>
 * @param isUserInputEnabled Boolean true可滑动
 * @return ViewPager2
 */
fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * 初始化ViewPager2
 * @receiver ViewPager2
 * @param fragmentActivity FragmentActivity
 * @param fragments ArrayList<Fragment>
 * @param isUserInputEnabled Boolean
 * @return ViewPager2
 */
fun ViewPager2.init(
    fragmentActivity: FragmentActivity,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
}

/**
 * MagicIndicator与ViewPager2绑定
 * @receiver MagicIndicator
 * @param viewPager2 ViewPager2
 * @param titles List<String>
 * @param action Function1<[@kotlin.ParameterName] Int, Unit>
 * @return MagicIndicator
 */
fun MagicIndicator.bindViewPager2(
    viewPager2: ViewPager2,
    titles: List<String> = arrayListOf(),
    action: (index: Int) -> Unit = {}
): MagicIndicator {
    setBackgroundColor(getColorExt(R.color.colorAccent))
    val commonNavigator = CommonNavigator(appContext)
    commonNavigator.adapter = object : CommonNavigatorAdapter() {
        override fun getCount(): Int {
            return titles.size
        }

        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            return ScaleTransitionPagerTitleView(appContext).apply {
                //设置文本
                text = titles[index].toHtml()
                //字体大小
                textSize = 17f
                //未选中颜色
                normalColor = Color.WHITE
                //选中颜色
                selectedColor = Color.WHITE
                //点击事件
                setOnClickListener {
                    viewPager2.currentItem = index
                    action.invoke(index)
                }
            }
        }

        override fun getIndicator(context: Context?): IPagerIndicator {
            return LinePagerIndicator(context).apply {
                mode = LinePagerIndicator.MODE_EXACTLY
                //线条的宽高度
                lineHeight = UIUtil.dip2px(appContext, 3.0).toFloat()
                lineWidth = UIUtil.dip2px(appContext, 30.0).toFloat()
                //线条的圆角
                roundRadius = UIUtil.dip2px(appContext, 6.0).toFloat()
                startInterpolator = AccelerateInterpolator()
                endInterpolator = DecelerateInterpolator(2.0f)
                //线条的颜色
                setColors(Color.WHITE)
            }
        }
    }
    this.navigator = commonNavigator
    ViewPagerHelper2.bind(this, viewPager2)
    return this
}

fun RecyclerView.initFloatBtn(floatbtn: FloatingActionButton) {
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
//            if (!canScrollVertically(-1)) {
//                floatbtn.visibility = View.INVISIBLE
//            }
            if (dy < 0) { // 当前处于上滑状态
                "当前处于上滑状态".logD()
                floatbtn.visibility = View.VISIBLE
            } else if (dy > 0) { // 当前处于下滑状态
                "当前处于下滑状态".logD()
                floatbtn.visibility = View.INVISIBLE
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            // OnScrollListener.SCROLL_STATE_FLING; //屏幕处于甩动状态
            // OnScrollListener.SCROLL_STATE_IDLE; //停止滑动状态
            // OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;// 手指接触状态
            //判断是否滑动到底部， recyclerView.canScrollVertically(1);返回false表示不能往上滑动，即代表到底部了；
            //判断是否滑动到顶部， recyclerView.canScrollVertically(-1);返回false表示不能往下滑动，即代表到顶部了；
            // 记录当前滑动状态
            if (newState == RecyclerView.SCROLL_STATE_IDLE) { //当前状态为停止滑动
                if (!canScrollVertically(1)) { // 到达底部
                    "到达底部".logD()
                } else if (!canScrollVertically(-1)) { // 到达顶部
                    "到达顶部".logD()
                    floatbtn.visibility = View.INVISIBLE
                }
            }
        }
    })
//    floatbtn.backgroundTintList = SettingUtil.getOneColorStateList(appContext)
    floatbtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
}































