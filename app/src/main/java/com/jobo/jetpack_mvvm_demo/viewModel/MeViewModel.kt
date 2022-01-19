package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.UserInfo
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.jetpack_mvvm_demo.data.model.bean.IntegralBean
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil

class MeViewModel : BaseViewModel() {
    var integralBean:IntegralBean? = null
    val integral = MutableLiveData<IntegralBean>()

    /**
     * 获取用户信息
     * @return UserInfo?
     */
    fun getUser(): UserInfo? {
        return CacheUtil.getUser()
    }

    /**
     * 获取积分
     */
    fun getIntegral() {
        rxHttpRequest {
            onRequest = {
                integral.value = UserRepository.getIntegral().await()
                integralBean = integral.value
            }
            onError = {
                integral.value = IntegralBean("0", "--", "--", "--")
            }
        }
    }
}