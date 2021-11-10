package com.jobo.commonmvvm.net

import androidx.annotation.IntDef

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/11/10 11:56 上午
 *
 */
@IntDef(LoadingType.LOADING_NULL, LoadingType.LOADING_DIALOG, LoadingType.LOADING_XML,LoadingType.LOADING_CUSTOM)
@Retention(AnnotationRetention.SOURCE)
annotation class LoadingType {
    companion object {
        //请求时不需要Loading
        const val LOADING_NULL = 0
        //请求时弹出 通用Dialog弹窗Loading
        const val LOADING_DIALOG = 1
        //请求时 界面 Loading Error Empty
        const val LOADING_XML = 2
        //请求时弹出 Loading-自定义 在 fragment/activity 使用时需要重写 showCustomLoading dismissCustomLoading 方法
        const val LOADING_CUSTOM = 3
    }
}