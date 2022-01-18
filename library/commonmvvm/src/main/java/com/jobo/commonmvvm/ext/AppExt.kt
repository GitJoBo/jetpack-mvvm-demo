package com.jobo.commonmvvm.ext

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import com.jobo.commonmvvm.base.appContext
import java.util.*

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/11/10 1:14 下午
 *
 */

/**
 * 获取当前进程的名称，默认进程名称是包名
 */
val currentProcessName: String?
    get() {
        val pid = android.os.Process.myPid()
        val mActivityManager = appContext.getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }

/**
 * 获取packageName
 */
fun getPackageNameName(context: Context): String {
    try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        return pi.packageName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取versionName
 */
fun getAppVersion(context: Context): String {
    try {
        val pi = context.packageManager.getPackageInfo(context.packageName, 0)
        return pi.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

private val activityList = Stack<Activity>()

/**
 * app当前显示的Activity
 * 注意{@link com.jobo.commonmvvm.utils.KtxActivityLifecycleCallbacks}
 */
val currentActivity: Activity? get() = if (activityList.isNullOrEmpty()) null else activityList.lastElement()

/**
 * 添加Activity入栈
 * @param activity Activity
 */
fun addActivity(activity: Activity) {
    activityList.add(activity)
}

/**
 * 关闭Activity出栈
 * @param activity Activity
 */
fun finishActivity(activity: Activity) {
    if (!activity.isFinishing) {
        activity.finish()
    }
    activityList.remove(activity)
}

/**
 * 从栈移除activity 不会finish
 * @param activity Activity
 */
fun removeActivity(activity: Activity) {
    activityList.remove(activity)
}

/**
 * 关闭Activity出栈
 * @param cls Class<*>
 */
fun finishActivity(cls: Class<*>) {
    if (activityList.isNullOrEmpty()) return
    val index = activityList.indexOfFirst { it.javaClass == cls }
    if (index == -1) return
    if (!activityList[index].isFinishing) {
        activityList[index].finish()
    }
    activityList.removeAt(index)
}

/**
 * 关闭所有的Activity 全部出栈
 */
fun finishAllActivity() {
    activityList.forEach {
        if (!it.isFinishing) {
            it.finish()
        }
    }
    activityList.clear()
}

/**
 * 关闭除栈顶的所有Activity
 */
fun finishAllNotTop() {
    val size = activityList.size - 2
    if (size >= 0) {
        for (i in 0..size){
            if (!activityList[i].isFinishing){
                activityList[i].finish()
            }
        }
    }
}
