package com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Desc: recyclerView的分割线
 * @author: admin wsj
 * @Date: 2021/12/8 3:49 下午
 *
 */
class SpaceItemDecoration
    (private val leftRight: Int, private val topBottom: Int, private val firstNeedTop: Boolean = true) : RecyclerView.ItemDecoration() {
//    constructor(
//        left: Int,
//        right: Int,
//        top: Int,
//        bottom: Int,
//        firstNeedTop: Boolean = true
//    ) : this(left, top, firstNeedTop) {
//
//    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
        val linearLayoutManager = parent.layoutManager as LinearLayoutManager
        //竖直方向的
        if (linearLayoutManager?.orientation == LinearLayoutManager.VERTICAL){
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == linearLayoutManager.itemCount - 1) {
                outRect.bottom = topBottom
            }
            if(!firstNeedTop&&parent.getChildAdapterPosition(view)==0){
                outRect.top = 0
            }else{
                outRect.top = topBottom
            }
            outRect.left = leftRight
            outRect.right = leftRight
        }else{
            //最后一项需要right
            if (parent.getChildAdapterPosition(view) != linearLayoutManager.itemCount - 1) {
                outRect.right = leftRight
            }
            outRect.top = topBottom
            outRect.left = 0
            outRect.bottom = topBottom
        }
    }
}