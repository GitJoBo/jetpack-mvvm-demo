package com.jobo.uicommon.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jobo.commonmvvm.base.appContext
import com.jobo.uicommon.base.ContainerActivity

/**
 * 跳转容器页面
 *
 * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
 */
fun toStartContainerActivity(context: Context?, canonicalName: String?) {
    toStartContainerActivity(context, canonicalName, null)
}

/**
 * 跳转容器页面
 *
 * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
 * @param bundle        跳转所携带的信息
 */
fun toStartContainerActivity(context: Context?, canonicalName: String?, bundle: Bundle?) {
    val intent = Intent(context, ContainerActivity::class.java)

    intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
    if (bundle != null) {
        intent.putExtra(ContainerActivity.BUNDLE, bundle)
    }
    if (context == null) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        appContext.startActivity(intent)
    } else {
        context.startActivity(intent)
    }
}