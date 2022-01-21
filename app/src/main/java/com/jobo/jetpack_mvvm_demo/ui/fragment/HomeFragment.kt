package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.net.LoadStatusEntity
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.databinding.FragmentHomeBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.SearchActivity
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.HomeViewModel
import com.jobo.jetpack_mvvm_demo.viewModel.RequestCollectViewModel
import com.jobo.uicommon.base.UIDBBaseFragment
import com.jobo.uicommon.ext.toStartContainerActivity

/**
 * @Desc: 首页
 * @author: admin wsj
 * @Date: 2021/12/2 10:03 上午
 *
 */
class HomeFragment : UIDBBaseFragment<HomeViewModel, FragmentHomeBinding>() {

    //适配器
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }

    //收藏viewModel
    private val mRequestCollectViewModel: RequestCollectViewModel by viewModels()

    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedTitle.titleBar.title = "首页"
        mBind.includedTitle.titleBar.titleView.setTextColor(getColorExt(R.color.white))
        mBind.includedTitle.titleBar.leftView.visibility = View.GONE
        mBind.includedTitle.titleBar.rightTitle = "搜索"
        mBind.includedTitle.titleBar.rightView.setTextColor(getColorExt(R.color.white))
        mBind.includedTitle.titleBar.rightView.setOnClickListener {
//            toStartContainerActivity(requireContext(), "com.jobo.jetpack_mvvm_demo.ui.fragment.SearchFragment")
            toStartActivity<SearchActivity>(requireActivity())
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
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            this.initFloatBtn(mBind.floatingActionButton)
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getList(true)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getList(false)
        }

        onLoadRetry()
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            titleBar(mBind.includedTitle.titleBar)
        }
    }

    override fun onRequestSuccess() {
        mViewModel.mListData.observe(viewLifecycleOwner, {
            mArticleAdapter.loadListSuccess(it, mBind.includedRV.smartRefreshLayout)
        })
    }

    /**
     * 请求失败
     * @param loadStatus LoadStatusEntity
     */
    override fun onRequestError(loadStatus: LoadStatusEntity) {
        when (loadStatus.requestCode) {
            NetUrl.HOME_LIST -> {
                //列表数据请求失败
                mArticleAdapter.loadListError(loadStatus, mBind.includedRV.smartRefreshLayout)
            }
        }
    }

    /**
     * 错误界面 空界面 点击重试
     */
    override fun onLoadRetry() {
        mViewModel.getList(isRefresh = true, loadingXml = true)
    }

    override fun getLoadingView(): View? {
        return mBind.includedRV.smartRefreshLayout
    }

    companion object {
        fun newInstance(args: Bundle): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}