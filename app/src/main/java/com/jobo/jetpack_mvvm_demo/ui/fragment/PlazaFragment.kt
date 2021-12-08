package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.base.BaseVbFragment
import com.jobo.commonmvvm.ext.*
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.app.ext.initFloatBtn
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.databinding.IncludeSmartRefreshRvFloatingActionButtonBinding
import com.jobo.jetpack_mvvm_demo.ui.activity.WebViewActivity
import com.jobo.jetpack_mvvm_demo.ui.adapter.ArticleAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.ThreeViewModel

/**
 * @Desc: 广场
 * @author: admin wsj
 * @Date: 2021/12/8 10:36 上午
 *
 */
class PlazaFragment : BaseVbFragment<ThreeViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf(), true) }


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
                    "关注".logD()
                } else {
                    "取消关注".logD()
                }
            }
            setOnItemClickListener { adapter, view, position ->
                "setOnItemClickListener".logD()
                val item = adapter.getItem(position) as ArticleResponse
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
    }
}