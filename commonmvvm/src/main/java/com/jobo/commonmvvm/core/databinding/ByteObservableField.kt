package com.jobo.commonmvvm.core.databinding

import androidx.databinding.ObservableField

/**
 * @Desc: 自定义的 Byte 类型 ObservableField  提供了默认值，避免取值的时候还要判空
 * @author: admin wsj
 * @Date: 2021/11/10 3:54 下午
 *
 */
class ByteObservableField(value: Byte = 0) : ObservableField<Byte>(value) {

    override fun get(): Byte {
        return super.get()!!
    }

}