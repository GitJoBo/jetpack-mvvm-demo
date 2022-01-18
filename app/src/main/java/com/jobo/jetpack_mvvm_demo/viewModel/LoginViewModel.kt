package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.jobo.commonmvvm.net.api.NetUrl
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.UserInfo
import com.jobo.commonmvvm.ext.logD
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.commonmvvm.net.LoadingType
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil

class LoginViewModel : BaseViewModel() {
    val login = MutableLiveData<UserInfo>()

    fun login(name: String, pass: String) {
        rxHttpRequest {
            onRequest = {
                login.value = UserRepository.postLogin(name, pass).await()
                GsonUtils.toJson(login.value).logD()
                CacheUtil.setUser(login.value)
            }
            loadingType = LoadingType.LOADING_DIALOG
            requestCode = NetUrl.LOGIN
        }
    }
}