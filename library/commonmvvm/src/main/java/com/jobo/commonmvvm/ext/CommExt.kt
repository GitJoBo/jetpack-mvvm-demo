package com.jobo.commonmvvm.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import com.jobo.commonmvvm.base.appContext

/**
 * @Desc: 一些常用的方法
 * @author: admin wsj
 * @Date: 2021/11/10 3:19 下午
 *
 */


val gson: Gson by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Gson() }

fun Any?.toJsonStr(): String {
    return gson.toJson(this)
}

fun Any?.toast() {
    ToastUtils.show(this)
}

/**
 * 关闭键盘
 */
fun EditText.hideKeyboard() {
    val imm = appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(
        this.windowToken,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

/**
 * 打开键盘
 */
fun EditText.openKeyboard() {
    this.apply {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
    }
    val inputManager =
        appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, 0)
}

/**
 * 关闭键盘焦点
 */
fun Activity.hideOffKeyboard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive && this.currentFocus != null) {
        if (this.currentFocus?.windowToken != null) {
            imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}

//fun toStartActivity(@NonNull clz: Class<*>) {
//    val intent = Intent(appContext, clz)
//    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    appContext.startActivity(intent)
//}

//fun toStartActivity(@NonNull clz: Class<*>, @NonNull bundle: Bundle) {
//    val intent = Intent(appContext, clz)
//    intent.apply {
//        putExtras(bundle)
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK
//    }
//    appContext.startActivity(intent)
//}
//
//fun toStartActivity(activity: Activity, @NonNull clz: Class<*>, code: Int, @NonNull bundle: Bundle) {
//    activity.startActivityForResult(Intent(appContext, clz).putExtras(bundle), code)
//}
//
//fun toStartActivity(fragment: Fragment, @NonNull clz: Class<*>, code: Int, @NonNull bundle: Bundle) {
//    fragment.startActivityForResult(Intent(appContext, clz).putExtras(bundle), code)
//}
//
//fun toStartActivity(activity: Activity, @NonNull intent: Intent, code: Int) {
//    activity.startActivityForResult(intent, code)
//}
//
//fun toStartActivity(@NonNull type: Any, @NonNull clz: Class<*>, code: Int, @NonNull bundle: Bundle) {
//    if (type is Activity) {
//        toStartActivity(type, clz, code, bundle)
//    } else if (type is Fragment) {
//        toStartActivity(type, clz, code, bundle)
//    }
//}

/**
 * 启动Activity
 * -FLAG_ACTIVITY_NEW_TASK
 */
inline fun <reified T : Activity> toStartActivity() {
    val intent = Intent(appContext, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    appContext.startActivity(intent)
}

/**
 * 启动Activity
 * @param activity Activity
 * example
 *      toStartActivity<MainActivity>(activity)
 */
inline fun <reified T : Activity> toStartActivity(activity: Activity) {
    activity.startActivity(Intent(activity, T::class.java))
}

/**
 * 启动Activity
 * @param activity Activity
 * @param bundle Bundle
 */
inline fun <reified T : Activity> toStartActivity(activity: Activity, bundle: Bundle) {
    val intent = Intent(activity, T::class.java)
    intent.putExtras(bundle)
    activity.startActivity(intent)
}

/**
 * 启动Activity
 * @param activity Activity
 * @param pair Array<out Pair<String, String>?>
 * example
 *      toStartActivity<MainActivity>(activity,Pair("KEY1", "VALUE1"),Pair("KEY2", "VALUE2"))
 */
inline fun <reified T : Activity> toStartActivity(activity: Activity, vararg pair: Pair<String, String>?) {
    val mIntent = Intent(activity, T::class.java)
    pair?.let {
        pair.forEach {
            mIntent.putExtra(it!!.first, it.second)
        }
    }
    activity.startActivity(mIntent)
}

/**
 * 隐藏状态栏
 */
fun hideStatusBar(activity: Activity) {
    val attrs = activity.window.attributes
    attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
    activity.window.attributes = attrs
}

/**
 * 显示状态栏
 */
fun showStatusBar(activity: Activity) {
    val attrs = activity.window.attributes
    attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
    activity.window.attributes = attrs
}

/**
 * 横竖屏
 */
fun isLandscape(context: Context) =
    context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * 应用商店
 */
fun gotoStore() {
    val uri =
        Uri.parse("market://details?id=" + appContext.packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    try {
        goToMarket.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        appContext.startActivity(goToMarket)
    } catch (ignored: ActivityNotFoundException) {
    }
}

/**
 * 字符串相等
 */
fun isEqualStr(value: String?, defaultValue: String?) =
    if (value.isNullOrEmpty() || defaultValue.isNullOrEmpty()) false else TextUtils.equals(
        value,
        defaultValue
    )

/**
 * Int类型相等
 *
 */
fun isEqualIntExt(value: Int, defaultValue: Int) = value == defaultValue

fun getDrawableExt(id: Int): Drawable? = ContextCompat.getDrawable(appContext, id)

fun getColorExt(id: Int): Int = ContextCompat.getColor(appContext, id)

fun getStringExt(id: Int) = appContext.resources.getString(id)

fun getStringArrayExt(id: Int): Array<String> = appContext.resources.getStringArray(id)

fun getIntArrayExt(id: Int) = appContext.resources.getIntArray(id)

fun getDimensionExt(id: Int) = appContext.resources.getDimension(id)

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}


