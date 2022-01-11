package com.jobo.commonmvvm.app.api

import rxhttp.wrapper.annotation.DefaultDomain
import java.util.*

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
     * 知识体系下的文章数据
     */
    const val ARTICLE_DATA_UNDER_THE_KNOWLEDGE_SYSTEM = "article/list/{page}/json"

    const val UPLOAD_URL = "http://t.xinhuo.com/index.php/Api/Pic/uploadPic"

    const val DOWNLOAD_URL = "http://update.9158.com/miaolive/Miaolive.apk"
}