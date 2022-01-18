package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.net.LoadStatusEntity
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.PlazaViewModel

class SystemChildFragment : BaseVbFragment<PlazaViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private var mCId = -1

    //适配器
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mCId = it.getInt("cid")
        }
        mArticleAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                "setOnItemClickListener".logD()
                val item = adapter.getItem(position) as ArticleBean
                toStartActivity<WebViewActivity>(requireActivity(), Pair(Config.TITLE, item.title), Pair(Config.URL, item.link))
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_home_author, R.id.item_project_author -> {
                        //TODO
                        "setOnItemChildClickListener".logD()
                    }
                }
            }
        }

        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mArticleAdapter
        }

        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getSystemChildData(mCId, true)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getSystemChildData(mCId)
        }

        onLoadRetry()
    }

    override fun onLoadRetry() {
        mViewModel.getSystemChildData(mCId, isRefresh = true, showLoadXml = true)
    }

    override fun onRequestSuccess() {
        mViewModel.systemChild.observe(viewLifecycleOwner,{
            mArticleAdapter.loadListSuccess(it,mBind.includedRV.smartRefreshLayout)
        })
    }

    override fun onRequestError(loadStatus: LoadStatusEntity) {
        super.onRequestError(loadStatus)
        when (loadStatus.requestCode) {
            NetUrl.ARTICLE_DATA_UNDER_THE_KNOWLEDGE_SYSTEM -> {
                //列表数据请求失败
                mArticleAdapter.loadListError(loadStatus, mBind.includedRV.smartRefreshLayout)
            }
        }
    }

    companion object {
//        fun newInstance(args: Bundle): SystemChildFragment {
//            val fragment = SystemChildFragment()
//            fragment.arguments = args
//            return fragment
//        }

        fun newInstance(cid: Int): SystemChildFragment {
            return SystemChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("cid", cid)
                }
            }
        }
    }
}