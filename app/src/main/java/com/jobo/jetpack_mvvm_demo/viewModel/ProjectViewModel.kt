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

class ProjectViewModel : BaseViewModel() {
    //页码 首页数据页码从0开始
    private var pageIndex = 0
    var mProjectTree: MutableLiveData<List<ClassifyBean>> = MutableLiveData()
    var mProjectList: MutableLiveData<ApiPagerResponse<ArticleBean>> = MutableLiveData()

    /**
     * 获取项目分类
     */
    fun getProjectTree() {
        rxHttpRequest {
            onRequest = {
                mProjectTree.value = UserRepository.getProjectTree().await()
            }
            requestCode = NetUrl.PROJECT_TREE
            loadingType = LoadingType.LOADING_XML
        }
    }

    /**
     *
     * @param isRefresh Boolean 是否是刷新
     * @param cid Int 项目分类id
     * @param loadingXml Boolean 请求时是否需要展示界面加载中loading
     */
    fun getList(isRefresh: Boolean, cid: Int = 0, loadingXml: Boolean = false) {
        if (isRefresh) {
            pageIndex = 0
        }
        rxHttpRequest {
            onRequest = {
                mProjectList.value = UserRepository.getProjectList(pageIndex, cid).await()
                pageIndex++
            }
            requestCode = if (cid == 0) NetUrl.PROJECT_LIST_NEW else NetUrl.PROJECT_LIST_ID
            loadingType = if (loadingXml) LoadingType.LOADING_XML else LoadingType.LOADING_NULL
        }
    }
}