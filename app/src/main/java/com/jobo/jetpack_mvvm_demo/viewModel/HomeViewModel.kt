package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.app.api.NetUrl
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.zhixinhuixue.zsyte.xxx.data.response.ApiResponse

class HomeViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
    var listData = MutableLiveData<ApiPagerResponse<ArticleResponse>>()

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
                listData.value = UserRepository.getList(pageIndex).await()
                pageIndex++
            }
            loadingType = if (loadingXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
            requestCode = NetUrl.HOME_LIST
            isRefreshRequest = isRefresh
        }
    }
}