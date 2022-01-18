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
import com.jobo.jetpack_mvvm_demo.viewModel.PlazaViewModel

/**
 * @Desc: 广场-每日一问
 * @author: admin wsj
 * @Date: 2021/12/8 10:42 上午
 *
 */
class AskFragment : BaseVbFragment<PlazaViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    private val mArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            initFloatBtn(mBind.floatingActionButton)
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getWendaList(true)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getWendaList(false)
        }
        mArticleAdapter.run {
            setCollectClick { item, v, position ->
                v.isChecked.logD()
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
        mViewModel.getWendaList(true, true)
    }

    override fun onRequestSuccess() {
        mViewModel.userArticleList.observe(viewLifecycleOwner, {
            mArticleAdapter.loadListSuccess(it, mBind.includedRV.smartRefreshLayout)
        })
    }
}