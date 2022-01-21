package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.launch
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.SearchBean
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil

class SearchViewModel : BaseViewModel() {
    var pageNo = 0

    /**
     * 历史数据
     */
    val historyData: MutableLiveData<MutableList<String>> = MutableLiveData()

    /**
     * 搜索热词数据
     */
    var hotData: MutableLiveData<MutableList<SearchBean>> = MutableLiveData()

    /**
     * 搜索结果数据
     */
    var seachResultData: MutableLiveData<ApiPagerResponse<ArticleBean>> =
        MutableLiveData()

    /**
     * 获取历史数据
     */
    fun getHistoryData() {
        launch({
            CacheUtil.getSearchHistoryData()
        }, {
            historyData.value = it
        }, {
            //获取本地历史数据出异常了
        })
        rxHttpRequest { }
    }

    /**
     * 获取搜索热词
     */
    fun getHistoryHot() {
        rxHttpRequest {
            onRequest = {
                hotData.value = UserRepository.getSearchHot().await()
            }
        }
    }

    /**
     * 搜索结果
     */
    fun getSearchResult(k: String, isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            pageNo = 0
        }
        rxHttpRequest {
            onRequest = {
                seachResultData.value = UserRepository.postSearchResult(pageNo, k).await()
                pageNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
            requestCode = NetUrl.SEARCH_RESULT
            isRefreshRequest = isRefresh
        }
    }


}