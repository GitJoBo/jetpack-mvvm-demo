package com.jobo.jetpack_mvvm_demo.data.repository

import com.jobo.commonmvvm.app.api.NetUrl
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.ClassifyResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.NavigationResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.SystemResponse
import com.zhixinhuixue.zsyte.xxx.data.response.ApiResponse
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

object UserRepository {
    /**
     * 获取列表信息
     */
    fun getList(pageIndex: Int): Await<ApiPagerResponse<ArticleResponse>> {
        return RxHttp.get(NetUrl.HOME_LIST, pageIndex)
            .toResponse()
    }

    /**
     * 获取项目分类
     */
    fun getProjectTree(): Await<MutableList<ClassifyResponse>> {
        return RxHttp.get(NetUrl.PROJECT_TREE).toResponse()
    }

    /**
     * 获取项目列表
     */
    fun getProjectList(pageNo: Int = 0, cid: Int = 0): Await<ApiPagerResponse<ArticleResponse>> {
        return if (cid == 0) {
            RxHttp.get(NetUrl.PROJECT_LIST_NEW)
                .addPath("page", pageNo)
                .toResponse()
        } else {
            RxHttp.get(NetUrl.PROJECT_LIST_ID)
                .addPath("page", pageNo)
                .addPath("cid", cid)
                .toResponse()
        }
    }

    /**
     * 获取广场列表
     */
    fun getUserArticleList(pageNo: Int): Await<ApiPagerResponse<ArticleResponse>> {
        return RxHttp.get(NetUrl.USER_ARTICLE_LIST)
            .addPath("page", pageNo)
            .toResponse()
    }

    /**
     * 获取每日一问列表
     */
    fun getWendaList(pageNo: Int): Await<ApiPagerResponse<ArticleResponse>> {
        return RxHttp.get(NetUrl.WENDA_LIST)
            .addPath("page", pageNo)
            .toResponse()
    }

    /**
     * 获取体系
     */
    fun getSystem(): Await<MutableList<SystemResponse>> {
        return RxHttp.get(NetUrl.TREE)
            .toResponse()
    }

    /**
     * 知识体系下的文章数据
     */
    fun getSystemChild(cid: Int, pageNo: Int): Await<ApiPagerResponse<ArticleResponse>> {
        return RxHttp.get(NetUrl.ARTICLE_DATA_UNDER_THE_KNOWLEDGE_SYSTEM)
            .addPath("page", pageNo)
            .add("cid", cid)
            .toResponse()
    }

    /**
     * 获取导航数据
     */
    fun getNavi(): Await<MutableList<NavigationResponse>> {
        return RxHttp.get(NetUrl.NAVI)
            .toResponse()
    }

    /**
     * 获取公众号分类
     */
    fun getWxArticle(): Await<ArrayList<ClassifyResponse>> {
        return RxHttp.get(NetUrl.WX_ARTICLE)
            .toResponse()
    }

    /**
     * 获取公众好列表
     */
    fun getWxArticleList(id: Int, pageNo: Int): Await<ApiPagerResponse<ArticleResponse>> {
        return RxHttp.get(NetUrl.WX_ARTICLE_LIST)
            .addPath("id", id)
            .addPath("page", pageNo)
            .toResponse()
    }


}