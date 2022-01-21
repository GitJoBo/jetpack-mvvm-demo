package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.jetpack_mvvm_demo.R

class SearchHistoryAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_history, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.item_history_text, item)
    }
}