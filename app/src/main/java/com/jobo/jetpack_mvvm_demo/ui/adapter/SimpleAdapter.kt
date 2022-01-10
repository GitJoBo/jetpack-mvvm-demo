package com.jobo.jetpack_mvvm_demo.ui.adapter

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.data.model.bean.WelcomeBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2022/1/10 1:31 下午
 *
 */
class SimpleAdapter : BaseBannerAdapter<WelcomeBean>() {

  var mOnSubViewClickListener: OnSubViewClickListener? = null

  override fun bindData(
    holder: BaseViewHolder<WelcomeBean>,
    data: WelcomeBean?,
    position: Int,
    pageSize: Int
  ) {
    val imageStart: ImageView = holder.findViewById(R.id.iv_logo)
    holder.setImageResource(R.id.banner_image, data!!.imageRes)
    holder.setOnClickListener(R.id.iv_logo) { view: View? ->
      if (null != mOnSubViewClickListener) mOnSubViewClickListener!!.onViewClick(
          view, holder.adapterPosition
      )
    }
    val alphaAnimator = ObjectAnimator.ofFloat(imageStart, "alpha", 0f, 1f)
    alphaAnimator.duration = 1500
    alphaAnimator.start()
  }

  override fun getLayoutId(viewType: Int): Int {
    return R.layout.item_custom_view;
  }

  interface OnSubViewClickListener {
    fun onViewClick(
      view: View?,
      position: Int
    )
  }
}
