package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleBean
import com.jobo.jetpack_mvvm_demo.data.model.bean.ClassifyBean
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository

class PublicViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
    val mWxArticle: MutableLiveData<ArrayList<ClassifyBean>> = MutableLiveData()
    val mWxArticleList: MutableLiveData<ApiPagerResponse<ArticleBean>> = MutableLiveData()


    /**
     * 公众号分类
     */
    fun getWxArticle() {
        rxHttpRequest {
            onRequest = {
                mWxArticle.value = UserRepository.getWxArticle().await()
            }
            requestCode = NetUrl.WX_ARTICLE
            loadingType = LoadingType.LOADING_XML
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
                mWxArticleList.value = UserRepository.getWxArticleList(cid, pageIndex).await()
                pageIndex++
            }
            loadingType = if (showLoadXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }
}