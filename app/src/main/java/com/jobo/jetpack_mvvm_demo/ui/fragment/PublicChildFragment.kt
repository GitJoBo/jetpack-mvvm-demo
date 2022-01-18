package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.PublicViewModel

class PublicChildFragment : BaseVbFragment<PublicViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    //改项目对应的id
    private var mCid = 0

    //适配器
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mCid = it.getInt("cid")
        }
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        mArticleAdapter.run {
            setOnItemClickListener { adapter, view, position ->
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
        mBind.includedRV.recyclerView.initFloatBtn(mBind.floatingActionButton)
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getList(mCid, true)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getList(mCid, false)
        }
    }

    override fun lazyLoadData() {
        mViewModel.getList(mCid, true, true)
    }

    override fun onRequestSuccess() {
        mViewModel.mWxArticleList.observe(viewLifecycleOwner,{
            mArticleAdapter.loadListSuccess(it,mBind.includedRV.smartRefreshLayout)
        })
    }

    companion object {
        fun newInstance(cid: Int): PublicChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            val fragment = PublicChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}