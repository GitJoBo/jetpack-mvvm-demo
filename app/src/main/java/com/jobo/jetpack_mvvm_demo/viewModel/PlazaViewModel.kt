package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.NavigationBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.SystemBean
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository

class PlazaViewModel : BaseViewModel() {
    private var paeNo = 0

    /**
     * 广场/每日一问
     */
    val userArticleList = MutableLiveData<ApiPagerResponse<ArticleBean>>()

    /**
     * 体系
     */
    val system = MutableLiveData<MutableList<SystemBean>>()

    /**
     * 知识体系下的文章数据
     */
    val systemChild = MutableLiveData<ApiPagerResponse<ArticleBean>>()

    /**
     * 导航
     */
    val navi = MutableLiveData<MutableList<NavigationBean>>()

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
    fun getSystem(showLoadXml: Boolean = false) {
        rxHttpRequest {
            onRequest = {
                system.value = UserRepository.getSystem().await()
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    /**
     * 获取体系子栏目列表数据
     */
    fun getSystemChildData(cid: Int = -1, isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            paeNo = 0
        }
        rxHttpRequest {
            onRequest = {
                systemChild.value = UserRepository.getSystemChild(cid, paeNo).await()
                paeNo++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    /**
     * 获取导航数据
     */
    fun getNavi(showLoadXml: Boolean = false) {
        rxHttpRequest {
            onRequest = {
                navi.value = UserRepository.getNavi().await()
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }
}