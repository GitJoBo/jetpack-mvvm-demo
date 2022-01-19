package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.IntegralBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.IntegralHistoryBean
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository

class IntegralViewModel : BaseViewModel() {
    private var pageNo = 0

    /**
     * 积分排行数据
     */
    var integralRank = MutableLiveData<ApiPagerResponse<IntegralBean>>()

    /**
     * 积分历史数据
     */
    var integralHistoryDataState = MutableLiveData<ApiPagerResponse<IntegralHistoryBean>>()

    fun getIntegralRank(isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            pageNo = 0
        }
        rxHttpRequest {
            onRequest = {
                integralRank.value = UserRepository.getIntegralRank(pageNo).await()
                pageNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    fun getIntegralHistory(isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            pageNo = 0
        }
        rxHttpRequest {
            onRequest = {
                integralHistoryDataState.value = UserRepository.getIntegralHistory(pageNo).await()
                pageNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

}