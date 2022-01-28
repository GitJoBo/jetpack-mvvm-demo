package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jobo.commonmvvm.ext.toHtml
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.CollectUrlBean
import com.jobo.jetpack_mvvm_demo.ui.weight.view.CollectView

class CollectUrlAdapter(data: MutableList<CollectUrlBean>) :
    BaseQuickAdapter<CollectUrlBean, BaseViewHolder>(R.layout.item_collecturl, data) {
    private var collectAction: (item: CollectUrlBean, v: CollectView, position: Int) -> Unit =
        { _: CollectUrlBean, _: CollectView, _: Int -> }

    init {
        setAnimationWithDefault(AnimationType.SlideInLeft)
    }

    override fun convert(holder: BaseViewHolder, item: CollectUrlBean) {
        //赋值
        item.run {
            holder.setText(R.id.item_collecturl_name, name.toHtml())
            holder.setText(R.id.item_collecturl_link, link)
            holder.getView<CollectView>(R.id.item_collecturl_collect).isChecked = true
        }
        holder.getView<CollectView>(R.id.item_collecturl_collect)
            .setOnCollectViewClickListener(object : CollectView.OnCollectViewClickListener {
                override fun onClick(v: CollectView) {
                    collectAction.invoke(item, v, holder.adapterPosition)
                }
            })
    }

    fun setCollectClick(inputCollectAction: (item: CollectUrlBean, v: CollectView, position: Int) -> Unit) {
        this.collectAction = inputCollectAction
    }
}