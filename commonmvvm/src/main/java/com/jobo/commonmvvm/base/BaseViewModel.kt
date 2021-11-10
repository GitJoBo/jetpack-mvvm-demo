package com.jobo.commonmvvm.base

import androidx.lifecycle.ViewModel
import com.jobo.commonmvvm.net.LoadStatusEntity
import com.jobo.commonmvvm.net.LoadingDialogEntity
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/18
 * 描述　:
 */
open class BaseViewModel : ViewModel() {

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的
     */
    //显示加载框
    inner class UiLoadingChange {
        //请求时loading
        val loading by lazy { UnPeekLiveData<LoadingDialogEntity>() }

        //界面显示空布局
        val showEmpty by lazy { UnPeekLiveData<LoadStatusEntity>() }

        //界面显示错误布局
        val showError by lazy { UnPeekLiveData<LoadStatusEntity>() }

        //界面显示错误布局
        val showSuccess by lazy { UnPeekLiveData<Boolean>() }
    }
}