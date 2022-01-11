package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.app.api.NetUrl
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.ClassifyResponse
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository

class PublicViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
    val wxArticle: MutableLiveData<ArrayList<ClassifyResponse>> = MutableLiveData()
    val wxArticleList: MutableLiveData<ApiPagerResponse<ArticleResponse>> = MutableLiveData()


    /**
     * 公众号分类
     */
    fun getWxArticle() {
        rxHttpRequest {
            onRequest = {
                wxArticle.value = UserRepository.getWxArticle().await()
            }
            requestCode = NetUrl.WX_ARTICLE
            loadingType = LoadingType.LOADING_NULL
        }
    }

    /**
     * 公众号列表
     * @param cid Int 分类id
     * @param isRefresh Boolean true刷新
     * @param showLoadXml Boolean true显示load view
     */
    fun getList(cid: Int = 0, isRefresh: Boolean = false, showLoadXml: Boolean = false) {
        if (isRefresh) {
            pageIndex = 0
        }
        rxHttpRequest {
            onRequest = {
                wxArticleList.value = UserRepository.getWxArticleList(cid, pageIndex).await()
                pageIndex++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }
}