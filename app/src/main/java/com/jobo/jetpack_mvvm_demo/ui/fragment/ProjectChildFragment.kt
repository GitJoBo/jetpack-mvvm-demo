package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import android.view.View
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
import com.jobo.jetpack_mvvm_demo.viewModel.TwoViewModel

class ProjectChildFragment : BaseVbFragment<TwoViewModel, IncludeSmartRefreshRvFloatingActionButtonBinding>() {
    //适配器
    private val mArticleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    //是否是最新项目
//    private var isNew = false

    //改项目对应的id
    private var cid = 0

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
//            isNew = getBoolean("isNew")
            cid = getInt("cid")
        }
        mBind.includedRV.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            adapter = mArticleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        mBind.includedRV.smartRefreshLayout.refresh {
            mViewModel.getList(true, cid)
        }
        mBind.includedRV.smartRefreshLayout.loadMore {
            mViewModel.getList(false, cid)
        }
        mArticleAdapter.run {
            setCollectClick { item, v, position ->
                if (v.isChecked) {
                    //TODO 取消关注请求
                    "取消关注请求".logD()
                } else {
                    //TODO 关注请求
                    "关注请求".logD()
                }
            }
            setOnItemClickListener { adapter, view, position ->
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
        mBind.includedRV.recyclerView.initFloatBtn(mBind.floatingActionButton)
//        mBind.floatingActionButton.visibility = View.VISIBLE
    }

    override fun lazyLoadData() {
        mViewModel.getList(true, cid, true)
    }

    override fun onRequestSuccess() {
        mViewModel.projectList.observe(viewLifecycleOwner, {
            mArticleAdapter.loadListSuccess(it, mBind.includedRV.smartRefreshLayout)
        })
    }

    companion object {
        fun newInstance(cid: Int, isNew: Boolean): ProjectChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            args.putBoolean("isNew", isNew)
            val fragment = ProjectChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}