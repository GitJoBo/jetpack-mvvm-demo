package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.refresh
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.SystemArrActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.SystemAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.PlazaViewModel

/**
 * @Desc: 广场-体系
 * @author: admin wsj
 * @Date: 2021/12/8 10:43 上午
 *
 */
class SystemFragment : BaseVbFragment<PlazaViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mSystemAdapter by lazy { SystemAdapter(arrayListOf()) }
    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mSystemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            initFloatBtn(mBind.floatingActionButton)
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getTree()
        }
        mBind.includedRV.smartRefreshLayout.setEnableLoadMore(false)
        mSystemAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val bundle = Bundle()
                bundle.putParcelable(Config.KEY, adapter.data[position] as Parcelable?)
                toStartActivity<SystemArrActivity>(requireActivity(),bundle)
            }
            setChildClick { data, view, position ->
                val bundle = Bundle()
                bundle.putParcelable(Config.KEY, data)
                bundle.putInt(Config.TYPE, position)
                toStartActivity<SystemArrActivity>(requireActivity(),bundle)
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getTree(true)
    }

    override fun onRequestSuccess() {
        mViewModel.system.observe(viewLifecycleOwner, {
            mSystemAdapter.setNewInstance(it)
        })
    }
}