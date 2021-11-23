package com.jobo.jetpack_mvvm_demo.ui.adapter

import com.jobo.jetpack_mvvm_demo.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class SplashBannerAdapter: BaseBannerAdapter<Int>() {
    override fun bindData(holder: BaseViewHolder<Int>?, data: Int?, position: Int, pageSize: Int) {
        holder?:return
        data?:return
        holder.setImageResource(R.id.banner_img,data)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_splach_banner
    }
}