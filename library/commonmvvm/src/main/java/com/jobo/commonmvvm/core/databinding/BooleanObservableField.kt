package com.jobo.commonmvvm.core.databinding

import androidx.databinding.ObservableField

/**
 * @Desc: 自定义的Boolean类型ObservableField 提供了默认值，避免取值的时候还要判空
 * @author: admin wsj
 * @Date: 2021/11/10 3:52 下午
 *
 */
class BooleanObservableField(value: Boolean = false) : ObservableField<Boolean>(value) {
    override fun get(): Boolean {
        return super.get()!!
    }
}