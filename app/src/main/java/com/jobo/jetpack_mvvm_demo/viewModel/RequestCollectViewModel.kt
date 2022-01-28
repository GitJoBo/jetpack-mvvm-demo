package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.*
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.jobo.jetpack_mvvm_demo.ui.adapter.CollectAdapter

/**
 * @Desc: 收藏
 * @author: admin wsj
 * @Date: 2022/1/18 4:44 下午
 *
 */
class RequestCollectViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
    var mProjectList: MutableLiveData<ApiPagerResponse<CollectBean>> = MutableLiveData()
    var mProjectUrlList: MutableLiveData<MutableList<CollectUrlBean>> = MutableLiveData()

    /**
     * 收藏文章
     */
    val favoriteArticles = MutableLiveData<CollectUiState>()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun favoriteArticles(id: String) {
        rxHttpRequest {
            onRequest = {
                val await = UserRepository.postFavoriteArticles(id).await()
                val uiState = CollectUiState(isSuccess = true, collect = true, id = id)
                favoriteArticles.value = uiState
            }
            onError = {
                val uiState = CollectUiState(isSuccess = false, collect = false, errorMsg = it.message, id = id)
                favoriteArticles.value = uiState
            }
        }
    }

    /**
     * 取消收藏
     * @param id String
     */
    fun unfavorite(id: String) {
        rxHttpRequest {
            onRequest = {
                val await = UserRepository.postUnfavorite(id).await()
                val uiState = CollectUiState(isSuccess = true, collect = false, id = id)
                favoriteArticles.value = uiState
            }

            onError = {
                val uiState =
                    CollectUiState(isSuccess = false, collect = false, errorMsg = it.message, id = id)
                favoriteArticles.value = uiState
            }
        }
    }

    /**
     * 收藏文章列表
     */
    fun favoritesList(isRefresh: Boolean = false, loadingXml: Boolean = false){
        if (isRefresh){
            pageIndex = 0
        }
        rxHttpRequest {
            onRequest = {
                mProjectList.value = UserRepository.getFavoriteArticleList(pageIndex).await()
                pageIndex++
            }
            loadingType = if (loadingXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

    /**
     * 收藏网址列表
     */
    fun favoriteURLList(isRefresh: Boolean = false,loadingXml: Boolean = false){
        if (isRefresh){
            pageIndex = 0
        }
        rxHttpRequest {
            onRequest = {
                mProjectUrlList.value = UserRepository.getFavoriteWebsiteList(pageIndex).await()
                pageIndex++
            }
            loadingType = if (loadingXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }

}