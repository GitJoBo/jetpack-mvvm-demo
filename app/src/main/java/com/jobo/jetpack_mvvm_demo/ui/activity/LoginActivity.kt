package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import android.view.View
import com.jobo.commonmvvm.ext.toStartActivity
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivityLoginBinding
import com.jobo.jetpack_mvvm_demo.viewModel.LoginViewModel
import com.jobo.uicommon.base.UIDBBaseActivity

class LoginActivity : UIDBBaseActivity<LoginViewModel, ActivityLoginBinding>(), View.OnClickListener {

    override fun initView(savedInstanceState: Bundle?) {
        mToolbar.title = "登录"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                toStartActivity<MainActivity>()
                finish()
            }
            R.id.tv_register -> {

            }
        }
    }
}