package com.jobo.jetpack_mvvm_demo.ui.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.IntegralBean
import com.jobo.jetpack_mvvm_demo.utils.SettingUtil

class IntegralAdapter(data: MutableList<IntegralBean>) : BaseQuickAdapter<IntegralBean, BaseViewHolder>(R.layout.item_integral, data) {
    private var rankNum: Int = -1

    constructor(data: ArrayList<IntegralBean>, rank: Int) : this(data) {
        this.rankNum = rank
    }

    override fun convert(holder: BaseViewHolder, item: IntegralBean) {
        //赋值
        item.run {
            if (rankNum == holder.adapterPosition + 1) {
                holder.setTextColor(R.id.item_integral_rank, SettingUtil.getColor(context))
                holder.setTextColor(R.id.item_integral_name, SettingUtil.getColor(context))
                holder.setTextColor(R.id.item_integral_count, SettingUtil.getColor(context))
            } else {
                holder.setTextColor(R.id.item_integral_rank, ContextCompat.getColor(context, R.color.colorBlack333))
                holder.setTextColor(R.id.item_integral_name, ContextCompat.getColor(context, R.color.colorBlack666))
                holder.setTextColor(R.id.item_integral_count, ContextCompat.getColor(context, R.color.textHint))
            }
            holder.setText(R.id.item_integral_rank, "${holder.adapterPosition + 1}")
            holder.setText(R.id.item_integral_name, username)
            holder.setText(R.id.item_integral_count, coinCount.toString())
        }
    }
}