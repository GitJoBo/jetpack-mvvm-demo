package com.jobo.jetpack_mvvm_demo.viewModel

import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.data.response.UserInfo
import com.jobo.jetpack_mvvm_demo.utils.CacheUtil

class MeViewModel : BaseViewModel() {
    fun getUser(): UserInfo? {
        return CacheUtil.getUser()
    }
}