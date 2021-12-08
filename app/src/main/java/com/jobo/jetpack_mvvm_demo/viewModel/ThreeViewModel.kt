package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.ClassifyResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.SystemResponse
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.zhixinhuixue.zsyte.xxx.data.response.ApiResponse

class ThreeViewModel : BaseViewModel() {
    private var paeNo = 0

    /**
     * 广场/每日一问
     */
    val userArticleList = MutableLiveData<ApiPagerResponse<ArticleResponse>>()

    /**
     * 体系
     */
    val tree = MutableLiveData<MutableList<SystemResponse>>()

    /**
     * 获取广场列表
     * @param isRefresh Boolean
     * @param showLoadXml Boolean true显示loading
     */
    fun getUserArticleList(isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            paeNo = 0
        }
        rxHttpRequest {
            onRequest = {
                userArticleList.value = UserRepository.getUserArticleList(paeNo).await()
                paeNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    /**
     * 获取每日一问列表
     */
    fun getWendaList(isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            paeNo = 0
        }
        rxHttpRequest {
            onRequest = {
                userArticleList.value = UserRepository.getWendaList(paeNo).await()
                paeNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    /**
     * 获取体系列表
     */
    fun getTree(showLoadXml: Boolean = false) {
        rxHttpRequest {
            onRequest = {
                tree.value = UserRepository.getTree().await()
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }
}