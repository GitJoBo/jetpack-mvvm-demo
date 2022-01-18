package com.jobo.commonmvvm.net.api

import com.jobo.commonmvvm.data.response.ApiPagerResponse
import com.jobo.commonmvvm.net.BaseNetConstant
import com.jobo.commonmvvm.data.response.ApiResponse
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.lang.reflect.Type

/**
 *
 * 作者　: hegaojian
 * 时间　: 2020/11/2
 * 描述　: 输入T,输出T,并对code统一判断
 */

@Parser(name = "Response", wrappers = [ApiPagerResponse::class])
open class ResponseParser<T> : TypeParser<T> {
    /**
     * 此构造方法适用于任意Class对象，但更多用于带泛型的Class对象，如：List<Student>
     *
     * 用法:
     * Java: .asParser(new ResponseParser<List<Student>>(){})
     * Kotlin: .asParser(object : ResponseParser<List<Student>>() {})
     *
     * 注：此构造方法一定要用protected关键字修饰，否则调用此构造方法将拿不到泛型类型
     */
    protected constructor() : super()

    /**
     * 此构造方法仅适用于不带泛型的Class对象，如: Student.class
     *
     * 用法
     * Java: .asParser(new ResponseParser<>(Student.class))   或者  .asResponse(Student.class)
     * Kotlin: .asParser(ResponseParser(Student::class.java)) 或者  .asResponse<Student>()
     */
    constructor(type: Type) : super(type)

    override fun onParse(response: okhttp3.Response): T {

        val data: ApiResponse<T> = response.convertTo(ApiResponse::class.java, *types)
//        val data: ApiResponse<T> = response.convertToParameterized(ApiResponse::class.java, *types)
        var t = data.data //获取data字段
        if (t == null && types[0] === String::class.java) {
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            @Suppress("UNCHECKED_CAST")
            t = data.errorMsg as T
        }

        val tData = t as? ApiPagerResponse<*>
        if (tData != null) {
            //如果返回值值列表封装类，且是第一页并且空数据 那么给空异常 让界面显示空
            if (tData.isRefresh() && tData.isEmpty()) {
                throw ParseException(BaseNetConstant.EMPTY_CODE, data.errorMsg, response)
            }
        }

        // errCode 不等于 SUCCESS_CODE，抛出异常
        if (data.errorCode != NetUrl.SUCCESS_CODE) {
            throw ParseException(data.errorCode.toString(), data.errorMsg, response)
        }
        return t
    }


}
