package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.hjq.toast.ToastUtils
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
//                toStartActivity<MainActivity>()
//                finish()
                val name = mBind.aetName.text.toString()
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("账号不能为空")
                    return
                }
                val pass = mBind.aetPass.text.toString()
                if (TextUtils.isEmpty(pass)) {
                    ToastUtils.show("密码不能为空")
                    return
                }
                mViewModel.login(name, pass)
            }
            R.id.tv_register -> {

            }
        }
    }

    override fun onRequestSuccess() {
        mViewModel.login.observe(this, {
            toStartActivity<MainActivity>()
            finish()
        })
    }
}