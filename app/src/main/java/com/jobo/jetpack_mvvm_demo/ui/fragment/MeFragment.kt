package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.jobo.commonmvvm.data.annotation.ValueKey
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.commonmvvm.net.api.NetHttpClient
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.FragmentMeBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.IntegralActivity
import com.jobo.jetpack_mvvm_demo.ui.activity.MyCollectionActivity
import com.jobo.jetpack_mvvm_demo.ui.weight.XCollapsingToolbarLayout
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil
import com.jobo.jetpack_mvvm_demo.viewModel.MeViewModel
import com.jobo.uicommon.base.UIVBBaseFragment
import com.scwang.smart.refresh.header.MaterialHeader

/**
 * @Desc: 我的
 * @author: admin wsj
 * @Date: 2022/1/11 2:42 下午
 *
 */
class MeFragment : UIVBBaseFragment<MeViewModel, FragmentMeBinding>(), XCollapsingToolbarLayout.OnScrimsListener {
    override fun initView(savedInstanceState: Bundle?) {
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(requireActivity(), mBind.tbHomeTitle)
        mBind.xcltlMe.setScrimsListener(this)

        mBind.smartRefreshLayout.setRefreshHeader(MaterialHeader(requireContext()))
        mBind.smartRefreshLayout.setOnRefreshListener {
            lazyLoadData()
        }
        mBind.meSignOutSb.setOnClickListener {
            NetHttpClient.sCookieStore.removeAllCookie()
            CacheUtil.setUser(null)
            lazyLoadData()
        }
        mBind.meSomeExamplesSb.setOnClickListener {

        }
        mBind.meCollectSb.setOnClickListener {
            toStartActivity<MyCollectionActivity>()
        }
        mBind.meIntegralSb.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(ValueKey.KEY, mViewModel.integralBean)
            toStartActivity<IntegralActivity>(requireActivity(), bundle)
        }
    }

    override fun onScrimsStateChange(layout: XCollapsingToolbarLayout?, shown: Boolean) {
        mBind.tvTitle.setTextColor(ContextCompat.getColor(requireActivity(), if (shown) R.color.black else R.color.white))
        mBind.tvTitle.visibility = if (shown) View.VISIBLE else View.GONE
    }

    override fun lazyLoadData() {
        mBind.tvTitle.text = mViewModel.getUser()?.nickname
        mBind.tvName.text = mViewModel.getUser()?.nickname
        mViewModel.getIntegral()
    }

    @SuppressLint("SetTextI18n")
    override fun onRequestSuccess() {
        mViewModel.integral.observe(viewLifecycleOwner) {
            mBind.smartRefreshLayout.finishRefresh()
            mBind.tvId.text = "id：${it.userId}  排名：${it.rank}"
            mBind.meSignOutSb.setRightText("当前积分： ${it.coinCount}")
        }
    }
}