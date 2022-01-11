package com.jobo.jetpack_mvvm_demo.ui.adapter

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jobo.jetpack_mvvm_demo.ui.fragment.PublicFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.HomeFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.PlazaFragment
import com.jobo.jetpack_mvvm_demo.ui.fragment.ProjectFragment

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
        fragments.put(PAGE_ONE, HomeFragment())
//        fragments.put(PAGE_ONE, TwoFragment())
        fragments.put(PAGE_TWO, ProjectFragment())
        fragments.put(PAGE_THREE, PlazaFragment())
        fragments.put(PAGE_FOUR, PublicFragment())
    }

    override fun getItemCount(): Int {
        return fragments.size()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}