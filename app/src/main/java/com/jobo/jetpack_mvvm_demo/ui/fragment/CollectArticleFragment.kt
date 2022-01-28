package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.CollectBean
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.CollectAdapter
import com.jobo.jetpack_mvvm_demo.viewModel.RequestCollectViewModel

/**
 * @Desc: 收藏的文章集合Fragment
 * @author: admin wsj
 * @Date: 2022/1/26 5:54 下午
 *
 */
class CollectArticleFragment : BaseVbFragment<RequestCollectViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mCollectAdapter: CollectAdapter by lazy { CollectAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.init(LinearLayoutManager(requireContext()), mCollectAdapter)
        mBind.includedRV.smartRefreshLayout.run {
            refresh {
                mViewModel.favoritesList(true)
            }
            loadMore {
                mViewModel.favoritesList()
            }
        }
        mBind.includedRV.recyclerView.initFloatBtn(mBind.floatingActionButton)
        mCollectAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    "取消关注请求".logD()
                    mViewModel.unfavorite(item.id)
                } else {
                    "关注请求".logD()
                    mViewModel.favoriteArticles(item.id)
                }
            }
            setOnItemClickListener { adapter, view, position ->
                val item = adapter.getItem(position) as CollectBean
                toStartActivity<WebViewActivity>(requireActivity(), Pair(Config.TITLE, item.title), Pair(Config.URL, item.link))
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.favoritesList(false, loadingXml = true)
    }

    override fun onRequestSuccess() {
        mViewModel.mProjectList.observe(viewLifecycleOwner) {
            mCollectAdapter.loadListSuccess(it, mBind.includedRV.smartRefreshLayout)
        }
    }
}