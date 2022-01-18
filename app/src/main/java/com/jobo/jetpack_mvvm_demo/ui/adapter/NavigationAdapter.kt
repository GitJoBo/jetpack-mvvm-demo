package com.jobo.jetpack_mvvm_demo.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jobo.commonmvvm.ext.toHtml
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.NavigationBean

class NavigationAdapter(data: ArrayList<NavigationBean>) :
    BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_system, data) {

    private var method: (data: ArticleBean, view: View, position: Int) -> Unit =
        { _: ArticleBean, _: View, _: Int -> }

    override fun convert(holder: BaseViewHolder, item: NavigationBean) {
        holder.setText(R.id.item_system_title, item.name.toHtml())
        holder.getView<RecyclerView>(R.id.item_system_rv).run {
            val foxayoutManager: FlexboxLayoutManager by lazy {
                FlexboxLayoutManager(context).apply {
                    //方向 主轴为水平方向，起点在左端
                    flexDirection = FlexDirection.ROW
                    //左对齐
                    justifyContent = JustifyContent.FLEX_START
                }
            }
            layoutManager = foxayoutManager
            setHasFixedSize(true)
            setItemViewCacheSize(200)
            isNestedScrollingEnabled = false
            adapter = NavigationChildAdapter(item.articles).apply {
                setOnItemClickListener { _, view, position ->
                    method(item.articles[position], view, position)
                }
            }

        }
    }


    fun setChildClick(method: (data: ArticleBean, view: View, position: Int) -> Unit) {
        this.method = method
    }

}