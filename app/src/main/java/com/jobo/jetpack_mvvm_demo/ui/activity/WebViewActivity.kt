package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import com.jobo.commonmvvm.ext.hideOffKeyboard
import com.jobo.commonmvvm.utils.Config
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivityWebviewBinding
import com.jobo.jetpack_mvvm_demo.viewModel.WebViewModel
import com.jobo.uicommon.base.UIDBBaseActivity
import com.just.agentweb.AgentWeb

class WebViewActivity : UIDBBaseActivity<WebViewModel, ActivityWebviewBinding>() {
    private var mAgentWeb: AgentWeb? = null

    private var mPreAgentWeb: AgentWeb.PreAgentWeb? = null

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.mShowTitle = intent.getStringExtra(Config.TITLE)
        mViewModel.mUrl = intent.getStringExtra(Config.URL)
        mToolbar.title = mViewModel.mShowTitle
        mToolbar.titleView.setTextColor(getColor(R.color.white))
        mToolbar.leftView.setOnClickListener {
            back()
        }

        mPreAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBind.fcvContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
        //加载网页
        mAgentWeb = mPreAgentWeb?.go(mViewModel.mUrl)
    }

    override fun onBackPressed() {
        back()
    }

    private fun back() {
        hideOffKeyboard()
        mAgentWeb?.run {
            if (webCreator.webView.canGoBack()) {
                webCreator.webView.goBack()
            } else {
                finish()
            }
        }
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