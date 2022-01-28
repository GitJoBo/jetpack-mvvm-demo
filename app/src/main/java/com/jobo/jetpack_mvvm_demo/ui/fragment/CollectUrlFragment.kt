package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.init
import com.jobo.commonmvvm.ext.loadListSuccess
import com.jobo.commonmvvm.ext.loadMore
import com.jobo.commonmvvm.ext.refresh
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.CollectUrlAdapter
import com.jobo.jetpack_mvvm_demo.viewModel.RequestCollectViewModel

class CollectUrlFragment : BaseVbFragment<RequestCollectViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mCollectUrlAdapter: CollectUrlAdapter by lazy {
        CollectUrlAdapter(arrayListOf())
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.init(LinearLayoutManager(requireContext()), mCollectUrlAdapter)
        mBind.includedRV.smartRefreshLayout.run {
            refresh {
                mViewModel.favoriteURLList(true)
            }
            loadMore {
                mViewModel.favoriteURLList()
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.favoriteURLList(isRefresh = true, loadingXml = true)
    }

    override fun onRequestSuccess() {
        mViewModel.mProjectUrlList.observe(viewLifecycleOwner){
//            mCollectUrlAdapter.loadListSuccess(it,mBind.includedRV.smartRefreshLayout)
            mCollectUrlAdapter.data.clear()
            mCollectUrlAdapter.data.addAll(it)
            mCollectUrlAdapter.notifyDataSetChanged()
        }
    }
}