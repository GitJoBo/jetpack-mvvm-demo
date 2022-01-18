package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.net.LoadStatusEntity
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.NavigationAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.PlazaViewModel

/**
 * @Desc: 广场-导航
 * @author: admin wsj
 * @Date: 2021/12/8 10:44 上午
 *
 */
class NavigationFragment : BaseVbFragment<PlazaViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mAdapter by lazy { NavigationAdapter(arrayListOf()) }
    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            initFloatBtn(mBind.floatingActionButton)
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getNavi()
        }
        mBind.includedRV.smartRefreshLayout.setEnableLoadMore(false)
        mAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                "setOnItemClickListener".logD()

            }
            setChildClick { data, view, position ->
                "setOnItemClickListener".logD()
                toStartActivity<WebViewActivity>(requireActivity(), Pair(Config.TITLE, data.title), Pair(Config.URL, data.link))
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getNavi(true)
    }

    override fun onRequestSuccess() {
        mViewModel.navi.observe(viewLifecycleOwner, {
            mAdapter.setNewInstance(it)
        })
    }

    override fun onRequestError(loadStatus: LoadStatusEntity) {
        super.onRequestError(loadStatus)
        when (loadStatus.requestCode) {
            NetUrl.NAVI -> {
                //列表数据请求失败
                mAdapter.loadListError(loadStatus, mBind.includedRV.smartRefreshLayout)
            }
        }
    }
}