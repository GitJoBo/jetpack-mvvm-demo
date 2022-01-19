package com.jobo.commonmvvm.net.api

import rxhttp.wrapper.annotation.DefaultDomain

object NetUrl {

    /**
     * 服务器请求成功的 Code值
     */
    const val SUCCESS_CODE = 0

    /**
     * 设置为默认域名
     */
    @DefaultDomain
    const val DEV_URL = "https://www.wanandroid.com"

    /**
     * 登录
     */
    const val LOGIN = "user/login"

    /**
     * 注册
     */
    const val REGISTER = "user/register"

    /**
     * 获取首页列表数据
     */
    const val HOME_LIST = "article/list/%1\$d/json"

    /**
     * 获取项目分类
     */
    const val PROJECT_TREE = "project/tree/json"

    /**
     * 获取最新项目列表数据
     */
    const val PROJECT_LIST_NEW = "article/listproject/{page}/json"

    /**
     * 根据分类id获取项目数据
     */
    const val PROJECT_LIST_ID = "project/list/{page}/json"

    /**
     * 广场列表数据
     */
    const val USER_ARTICLE_LIST = "user_article/list/{page}/json"

    /**
     * 每日一问
     */
    const val WENDA_LIST = "wenda/list/{page}/json"

    /**
     * 体系
     */
    const val TREE = "tree/json"

    /**
     * 获取导航数据
     */
    const val NAVI = "navi/json"

    /**
     * 知识体系下的文章数据
     */
    const val ARTICLE_DATA_UNDER_THE_KNOWLEDGE_SYSTEM = "article/list/{page}/json"

    /**
     * 公众号分类
     */
    const val WX_ARTICLE = "wxarticle/chapters/json"

    /**
     * 公众号列表
     */
    const val WX_ARTICLE_LIST = "wxarticle/list/{id}/{page}/json"

    /**
     * 收藏文章
     */
    const val FAVORITE_ARTICLES = "lg/collect/{id}/json"

    /**
     * 取消收藏
     */
    const val UN_FAVORITE = "lg/uncollect_originId/{id}/json"

    /**
     * 获取当前账户的个人积分
     */
    const val ME_INTEGRAL = "lg/coin/userinfo/json"

    /**
     * 获取积分排行榜
     */
    const val INTEGRAL_RANK = "coin/rank/{page}/json"

    /**
     * 获取积分历史
     */
    const val INTEGRAL_HISTORY = "lg/coin/list/{page}/json"


    const val UPLOAD_URL = "http://t.xinhuo.com/index.php/Api/Pic/uploadPic"

    const val DOWNLOAD_URL = "http://update.9158.com/miaolive/Miaolive.apk"
}