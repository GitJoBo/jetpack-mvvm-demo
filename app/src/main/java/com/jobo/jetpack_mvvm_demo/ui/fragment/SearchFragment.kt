package com.jobo.jetpack_mvvm_demo.ui.fragment

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jobo.commonmvvm.data.annotation.ValueKey
import com.jobo.commonmvvm.ext.*
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.FragmentSearchBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.SearchHistoryAdapter
import com.jobo.jetpack_mvvm_demo.ui.adapter.SearchHotAdapter
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil
import com.jobo.jetpack_mvvm_demo.utils.SettingUtil
import com.jobo.jetpack_mvvm_demo.viewModel.SearchViewModel
import com.jobo.uicommon.base.UIVBBaseFragment

/**
 * @Desc: 搜索
 * @author: admin wsj
 * @Date: 2022/1/20 9:43 上午
 *
 */
class SearchFragment : UIVBBaseFragment<SearchViewModel, FragmentSearchBinding>() {
    private val mSearchHistoryAdapter: SearchHistoryAdapter by lazy { SearchHistoryAdapter(arrayListOf()) }
    private val mSearchHotAdapter: SearchHotAdapter by lazy { SearchHotAdapter(arrayListOf()) }

    override fun initView(savedInstanceState: Bundle?) {
        mBind.searchHistoryRv.init(LinearLayoutManager(requireContext()), mSearchHistoryAdapter, false)
        //初始化热门Recyclerview
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        //方向 主轴为水平方向，起点在左端
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        //左对齐
        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
        mBind.searchHotRv.run {
            adapter = mSearchHotAdapter
            layoutManager = flexboxLayoutManager
            isNestedScrollingEnabled = false
        }
        mBind.ivSearch.setOnClickListener {
            updateKey(mBind.cetSearch.text.toString())
            "搜索结果0".logD()
        }
        mBind.searchClear.setOnClickListener {
            activity?.let {
                MaterialDialog(it)
                    .cancelable(false)
                    .lifecycleOwner(this)
                    .show {
                        title(text = "温馨提示")
                        message(text = "确定清空吗?")
                        negativeButton(text = "取消")
                        positiveButton(text = "清空") {
                            //清空
                            mViewModel.historyData.value = arrayListOf()
                        }
                        getActionButton(WhichButton.POSITIVE).updateTextColor(
                            SettingUtil.getColor(
                                it
                            )
                        )
                        getActionButton(WhichButton.NEGATIVE).updateTextColor(
                            SettingUtil.getColor(
                                it
                            )
                        )
                    }
            }
        }
        mSearchHistoryAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = mSearchHistoryAdapter.data[position]
                updateKey(queryStr)
                "搜索结果".logD()
            }
            addChildClickViewIds(R.id.item_history_del)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_history_del -> {
                        mViewModel.historyData.value?.let {
                            it.removeAt(position)
                            mViewModel.historyData.value = it
                        }
                    }
                }
            }
        }

        mSearchHotAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val queryStr = mSearchHotAdapter.data[position].name
                updateKey(queryStr)
                "搜索结果2".logD()
            }
        }
    }

    override fun lazyLoadData() {
        mViewModel.getHistoryData()
        mViewModel.getHistoryHot()
    }

    override fun showToolBar(): Boolean {
        return true
    }

    override fun onRequestSuccess() {
        mViewModel.hotData.observe(viewLifecycleOwner, {
            mSearchHotAdapter.setNewInstance(it)
        })
        mViewModel.historyData.observe(viewLifecycleOwner, {
            mSearchHistoryAdapter.data = it
            mSearchHistoryAdapter.notifyDataSetChanged()
            CacheUtil.setSearchHistoryData(it.toJson())
        })
    }

    /**
     * 更新搜索词
     */
    fun updateKey(keyStr: String) {
        mViewModel.historyData.value?.let {
            if (it.contains(keyStr)) {
                //当搜索历史中包含该数据时 删除
                it.remove(keyStr)
            } else if (it.size >= 10) {
                //如果集合的size 有10个以上了，删除最后一个
                it.removeAt(it.size - 1)
            }
            //添加新数据到第一条
            it.add(0, keyStr)
            mViewModel.historyData.value = it
        }
        nav().navigateAction(R.id.action_searchFragment_to_searchResultFragment,
            Bundle().apply {
                putString(ValueKey.KEY, keyStr)
            }
        )
    }
}