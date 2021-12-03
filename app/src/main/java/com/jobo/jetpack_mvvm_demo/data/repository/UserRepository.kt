package com.jobo.jetpack_mvvm_demo.data.repository

import com.jobo.commonmvvm.app.api.NetUrl
import com.jobo.commonmvvm.app.api.ResponseParser
import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.jetpack_mvvm_demo.data.model.bean.ArticleResponse
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
}