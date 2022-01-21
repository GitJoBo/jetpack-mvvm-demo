package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.SearchBean
import com.jobo.jetpack_mvvm_demo.utils.ColorUtil

class SearchHotAdapter(data: MutableList<SearchBean>) : BaseQuickAdapter<SearchBean, BaseViewHolder>(R.layout.flow_layout, data) {
    override fun convert(holder: BaseViewHolder, item: SearchBean) {
        holder.setText(R.id.flow_tag, item.name)
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }
}