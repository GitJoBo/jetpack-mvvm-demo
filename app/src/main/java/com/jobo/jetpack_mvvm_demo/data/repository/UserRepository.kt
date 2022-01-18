package com.jobo.jetpack_mvvm_demo.data.repository

import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.data.response.UserInfo
import com.jobo.jetpack_mvvm_demo.data.model.bean.*
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toResponse

object UserRepository {

    /**
     * 获取列表信息
     */
    fun getList(pageIndex: Int): Await<ApiPagerResponse<ArticleBean>> {
        return RxHttp.get(NetUrl.HOME_LIST, pageIndex)
            .toResponse()
    }

    /**
     * 获取项目分类
     */
    fun getProjectTree(): Await<MutableList<ClassifyBean>> {
        return RxHttp.get(NetUrl.PROJECT_TREE).toResponse()
    }

    /**
     * 获取项目列表
     */
    fun getProjectList(pageNo: Int = 0, cid: Int = 0): Await<ApiPagerResponse<ArticleBean>> {
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
    fun getUserArticleList(pageNo: Int): Await<ApiPagerResponse<ArticleBean>> {
        return RxHttp.get(NetUrl.USER_ARTICLE_LIST)
            .addPath("page", pageNo)
            .toResponse()
    }

    /**
     * 获取每日一问列表
     */
    fun getWendaList(pageNo: Int): Await<ApiPagerResponse<ArticleBean>> {
        return RxHttp.get(NetUrl.WENDA_LIST)
            .addPath("page", pageNo)
            .toResponse()
    }

    /**
     * 获取体系
     */
    fun getSystem(): Await<MutableList<SystemBean>> {
        return RxHttp.get(NetUrl.TREE)
            .toResponse()
    }

    /**
     * 知识体系下的文章数据
     */
    fun getSystemChild(cid: Int, pageNo: Int): Await<ApiPagerResponse<ArticleBean>> {
        return RxHttp.get(NetUrl.ARTICLE_DATA_UNDER_THE_KNOWLEDGE_SYSTEM)
            .addPath("page", pageNo)
            .add("cid", cid)
            .toResponse()
    }

    /**
     * 获取导航数据
     */
    fun getNavi(): Await<MutableList<NavigationBean>> {
        return RxHttp.get(NetUrl.NAVI)
            .toResponse()
    }

    /**
     * 获取公众号分类
     */
    fun getWxArticle(): Await<ArrayList<ClassifyBean>> {
        return RxHttp.get(NetUrl.WX_ARTICLE)
            .toResponse()
    }

    /**
     * 获取公众好列表
     */
    fun getWxArticleList(id: Int, pageNo: Int): Await<ApiPagerResponse<ArticleBean>> {
        return RxHttp.get(NetUrl.WX_ARTICLE_LIST)
            .addPath("id", id)
            .addPath("page", pageNo)
            .toResponse()
    }

    /**
     * 登录
     */
    fun postLogin(username: String, password: String): Await<UserInfo> {
        return RxHttp.postForm(NetUrl.LOGIN)
            .add("username", username)
            .add("password", password)
            .toResponse()
    }

//    /**
//     * 设置用户信息
//     */
//    fun setUser(user: UserInfo) {
//        mmkv.encode(ValueKey.USER, user)
//    }
//
//    /**
//     * 获取登录用户信息
//     * @return UserBean?
//     */
//    fun getUser(): UserInfo? {
//        if (user == null) {
//            user = mmkv.decodeParcelable(ValueKey.USER, UserInfo::class.java)
//        }
//        return user
//    }


}