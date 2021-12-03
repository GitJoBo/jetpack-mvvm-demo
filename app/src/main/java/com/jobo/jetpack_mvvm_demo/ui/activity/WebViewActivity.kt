package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.jobo.commonmvvm.base.BaseVbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivityWebviewBinding
import com.jobo.jetpack_mvvm_demo.viewModel.WebViewModel
import com.jobo.uicommon.base.UIBaseActivity
import com.just.agentweb.AgentWeb

class WebViewActivity : UIBaseActivity<WebViewModel, ActivityWebviewBinding>() {
    private var mAgentWeb: AgentWeb? = null

    private var mPreAgentWeb: AgentWeb.PreAgentWeb? = null

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.mShowTitle = intent.getStringExtra(Config.TITLE)
        mViewModel.mUrl = intent.getStringExtra(Config.URL)
        mToolbar.title = mViewModel.mShowTitle
        mToolbar.titleView.setTextColor(getColor(R.color.white))

        mPreAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBind.fcvContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
        //加载网页
        mAgentWeb = mPreAgentWeb?.go(mViewModel.mUrl)
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

}