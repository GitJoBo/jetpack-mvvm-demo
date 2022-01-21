package com.jobo.commonmvvm.navigation

import android.view.View
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.jobo.commonmvvm.R

/**
 * @Desc: Hide - Show NavHostFragment
 * @author: admin wsj
 * @Date: 2022/1/20 11:20 上午
 *
 */
class NavHostFragmentHideShow : NavHostFragment() {
    /**
     * @return 使用自己的FragmentNavigator
     */
    override fun createFragmentNavigator(): Navigator<out FragmentNavigator.Destination?> {
        return FragmentNavigatorHideShow(requireContext(), childFragmentManager, containerId)
    }


    private val containerId: Int
        get() {
            val id = id
            return if (id != 0 && id != View.NO_ID) {
                id
                // 如果此 Fragment 不是通过添加的，则回退到使用我们自己的 ID
                // add(containerViewId, Fragment)
            } else R.id.nav_host_fragment_container
        }
}