package com.jobo.commonmvvm.app.api

import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * @Desc: 头部拦截器
 * @author: admin wsj
 * @Date: 2021/11/19 1:59 下午
 *
 */
class HeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        //模拟了2个公共参数
        builder.addHeader("token", "token123456").build()
        builder.addHeader("device", "Android").build()
        return chain.proceed(builder.build())
    }

}