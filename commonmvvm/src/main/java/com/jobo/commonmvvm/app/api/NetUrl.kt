package com.jobo.commonmvvm.app.api

import rxhttp.wrapper.annotation.DefaultDomain

object NetUrl {

    // 服务器请求成功的 Code值
    const val SUCCESS_CODE = 0

    @DefaultDomain //设置为默认域名
    const val DEV_URL = "https://www.wanandroid.com"

    //登录
    const val LOGIN = "user/login"
    //获取首页列表数据
    const val HOME_LIST = "article/list/%1\$d/json"

    const val UPLOAD_URL = "http://t.xinhuo.com/index.php/Api/Pic/uploadPic"

    const val DOWNLOAD_URL = "http://update.9158.com/miaolive/Miaolive.apk"
}