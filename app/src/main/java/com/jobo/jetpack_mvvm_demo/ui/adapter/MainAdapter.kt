package com.jobo.jetpack_mvvm_demo.ui.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jobo.jetpack_mvvm_demo.ui.fragment.FourFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.OneFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.ThreeFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.TwoFragment

@Deprecated(message = "使用 ViewPager2.initMain")
class MainAdapter(fA: FragmentActivity) : FragmentStateAdapter(fA) {
    companion object {
        const val PAGE_ONE = 0
        const val PAGE_TWO = 1
        const val PAGE_THREE = 2
        const val PAGE_FOUR = 3
    }

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(PAGE_ONE, OneFragment())
//        fragments.put(PAGE_ONE, TwoFragment())
        fragments.put(PAGE_TWO, TwoFragment())
        fragments.put(PAGE_THREE, ThreeFragment())
        fragments.put(PAGE_FOUR, FourFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}