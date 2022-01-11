package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.commonmvvm.ext.toHtml
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.NavigationResponse
import com.jobo.jetpack_mvvm_demo.utils.ColorUtil

class NavigationChildAdapter(data: ArrayList<ArticleResponse>) :
    BaseQuickAdapter<ArticleResponse, BaseViewHolder>(R.layout.flow_layout, data) {

//    init {
//        setAdapterAnimation(SettingUtil.getListMode())
//    }

    override fun convert(holder: BaseViewHolder, item: ArticleResponse) {
        holder.setText(R.id.flow_tag, item.title.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}