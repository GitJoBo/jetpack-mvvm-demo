package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.commonmvvm.ext.toHtml
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.ClassifyBean
import com.jobo.jetpack_mvvm_demo.utils.ColorUtil

class SystemChildAdapter(data: ArrayList<ClassifyBean>) :
    BaseQuickAdapter<ClassifyBean, BaseViewHolder>(R.layout.flow_layout, data) {

//    init {
//        setAdapterAnimation(SettingUtil.getListMode())
//    }

    override fun convert(holder: BaseViewHolder, item: ClassifyBean) {
        holder.setText(R.id.flow_tag, item.name.toHtml())
        holder.setTextColor(R.id.flow_tag, ColorUtil.randomColor())
    }

}