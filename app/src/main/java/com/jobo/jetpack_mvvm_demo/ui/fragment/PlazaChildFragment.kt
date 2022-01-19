package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.GsonUtils
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.eventViewModel
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.event.CollectBus
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.PlazaViewModel
import com.jobo.jetpack_mvvm_demo.viewModel.RequestCollectViewModel
import com.jobo.uicommon.ext.showMessage

/**
 * @Desc: 广场
 * @author: admin wsj
 * @Date: 2021/12/8 10:36 上午
 *
 */
class PlazaChildFragment : BaseVbFragment<PlazaViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }

    //收藏viewModel
    private val mRequestCollectViewModel: RequestCollectViewModel by viewModels()


    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            this.initFloatBtn(mBind.floatingActionButton)
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getUserArticleList(isRefresh = true, showLoadXml = false)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getUserArticleList(false)
        }
        mArticleAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    "取消关注".logD()
                    mRequestCollectViewModel.unfavorite(item.id)
                } else {
                    "关注".logD()
                    mRequestCollectViewModel.favoriteArticles(item.id)
                }
            }
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
    }

    override fun lazyLoadData() {
        mViewModel.getUserArticleList(isRefresh = true, showLoadXml = true)
    }

    override fun onRequestSuccess() {
        mViewModel.userArticleList.observe(viewLifecycleOwner, {
            mArticleAdapter.loadListSuccess(it, mBind.includedRV.smartRefreshLayout)
        })

        mRequestCollectViewModel.favoriteArticles.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                //收藏或取消收藏操作成功，发送全局收藏消息
                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
            } else {
                it.errorMsg?.run {
                    showMessage(this)
                }
                for (index in mArticleAdapter.data.indices) {
                    if (mArticleAdapter.data[index].id == it.id) {
                        if (mArticleAdapter.data[index].collect != it.collect) {
                            mArticleAdapter.data[index].collect = it.collect
//                            mArticleAdapter.notifyItemChanged(index)
                        }
                        break
                    }
                }
            }
        })

        //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
        eventViewModel.collectEvent.observe(viewLifecycleOwner, {
            GsonUtils.toJson(it).logD()
            for (index in mArticleAdapter.data.indices) {
                if (mArticleAdapter.data[index].id == it.id) {
                    if (mArticleAdapter.data[index].collect != it.collect) {
                        mArticleAdapter.data[index].collect = it.collect
//                        mArticleAdapter.notifyItemChanged(index)
                    }
                    break
                }
            }
        })
    }
}