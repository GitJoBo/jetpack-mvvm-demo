package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobo.commonmvvm.data.annotation.ValueKey
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.databinding.FragmentSearchResultBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.viewModel.RequestCollectViewModel
import com.jobo.jetpack_mvvm_demo.viewModel.SearchViewModel
import com.jobo.uicommon.base.UIVBBaseFragment

/**
 * @Desc: 搜索结果
 * @author: admin wsj
 * @Date: 2022/1/20 4:39 下午
 *
 */
class SearchResultFragment : UIVBBaseFragment<SearchViewModel, FragmentSearchResultBinding>() {
    var mKey: String = ""
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }

    //收藏viewModel
    private val mRequestCollectViewModel: RequestCollectViewModel by viewModels()
    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let { arguments -> arguments.getString(ValueKey.KEY)?.let { mKey = it } }
        mToolbar?.title = mKey
        mToolbar?.titleView?.setTextColor(getColorExt(R.color.white))
        mBind.includedSmartRefreshRv.includedRV.recyclerView.init(LinearLayoutManager(requireContext()), mArticleAdapter, false)
        mBind.includedSmartRefreshRv.includedRV.smartRefreshLayout.refresh {
            mViewModel.getSearchResult(mKey, true)
        }
        mBind.includedSmartRefreshRv.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getSearchResult(mKey)
        }
        mArticleAdapter.run {
            setCollectClick { item, v, _ ->
                if (v.isChecked) {
                    "取消关注请求".logD()
                    mRequestCollectViewModel.unfavorite(item.id)
                } else {
                    "关注请求".logD()
                    mRequestCollectViewModel.favoriteArticles(item.id)
                }
            }
            setOnItemClickListener { adapter, view, position ->
                //TODO 详情
//                KLog.d("setOnItemClickListener")
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
    }

    override fun lazyLoadData() {
        mViewModel.getSearchResult(mKey, true, showLoadXml = true)
    }

    override fun showToolBar(): Boolean {
        return true
    }

    override fun onRequestSuccess() {
        mViewModel.seachResultData.observe(viewLifecycleOwner, {
            mArticleAdapter.loadListSuccess(it, mBind.includedSmartRefreshRv.includedRV.smartRefreshLayout)
        })
    }

    override fun getLoadingView(): View? {
        return mBind.includedSmartRefreshRv.includedRV.smartRefreshLayout
    }
}