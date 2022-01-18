package com.jobo.uicommon.ui.activity

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.hjq.toast.ToastUtils
import com.jobo.commonmvvm.base.BaseDbActivity
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.uicommon.databinding.ActivityErrorBinding
import com.jobo.uicommon.ext.clipboardManager
import com.jobo.uicommon.ext.showMessage

class ErrorActivity : BaseDbActivity<BaseViewModel, ActivityErrorBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        mBind.errorRestart.clickNoRepeat {
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        mBind.errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it, "发现有Bug不去打作者脸？", "必须打", {
                    val mClipData = ClipData.newPlainText("errorLog", it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.show("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.show("请先安装QQ")
                    }
                }, "我不敢")
            }
        }
    }

}