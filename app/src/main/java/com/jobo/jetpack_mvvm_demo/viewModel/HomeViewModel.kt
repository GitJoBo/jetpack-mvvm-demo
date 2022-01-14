package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jobo.commonmvvm.app.api.NetUrl
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.commonmvvm.net.interception.logging.util.LogUtils
import com.jobo.commonmvvm.utils.XLog
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse
import rxhttp.wrapper.utils.GsonUtil

class HomeViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
//    var listData = MutableLiveData<ApiPagerResponse<ArticleResponse>>()//Explanation for issues of type "NullSafeMutableLiveData"   LiveData bug？
    var listData :MutableLiveData<ApiPagerResponse<ArticleResponse>> = MutableLiveData<ApiPagerResponse<ArticleResponse>>()// 这样可以
    //Explanation for issues of type "NullSafeMutableLiveData":
    //   This check ensures that LiveData values are not null when explicitly
    //            declared as non-nullable.
    //
    //                   Kotlin interoperability does not support enforcing explicit
    //   null-safety when using                 generic Java type parameters. Since
    //   LiveData is a Java class its value can always                 be null even
    //   when its type is explicitly declared as non-nullable. This can lead
    //           to runtime exceptions from reading a null LiveData value that is
    //   assumed to be                 non-nullable.
    //
    //   Vendor: Android Open Source Project (jetified-lifecycle-livedata-core-ktx-2.4.0)
    //   Identifier: jetified-lifecycle-livedata-core-ktx-2.4.0
    //   Feedback: https://issuetracker.google.com/issues/new?component=192731
    //
    //10 errors, 0 warnings
    //Lint found fatal errors while assembling a release target.
    //
    //To proceed, either fix the issues identified by lint, or modify your build script as follows:
    //...
    //android {
    //    lintOptions {
    //        checkReleaseBuilds false
    //        // Or, if you prefer, you can continue to check for errors in release builds,
    //        // but continue the build even when errors are found:
    //        abortOnError false
    //    }
    //}
    /**
     *
     * @param isRefresh Boolean 是否是刷新
     * @param loadingXml Boolean 请求时是否需要展示界面加载中loading
     */
    fun getList(isRefresh: Boolean, loadingXml: Boolean = false) {
        if (isRefresh) {
            pageIndex = 0
        }
        rxHttpRequest {
            onRequest = {
                val await = UserRepository.getList(pageIndex).await()
                XLog.d(GsonUtil.toJson(await))
                listData.value = await
                pageIndex++
            }
            loadingType = if (loadingXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
            requestCode = NetUrl.HOME_LIST
            isRefreshRequest = isRefresh
        }
    }
}