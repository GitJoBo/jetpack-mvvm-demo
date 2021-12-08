package com.jobo.jetpack_mvvm_demo.app

import android.view.Gravity
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.effective.android.anchors.Project
import com.effective.android.anchors.Task
import com.effective.android.anchors.TaskCreator
import com.hjq.toast.ToastUtils
import com.jobo.commonmvvm.base.appContext
import com.jobo.commonmvvm.ext.dp
import com.jobo.commonmvvm.ext.getColorExt
import com.jobo.commonmvvm.state.BaseEmptyCallback
import com.jobo.commonmvvm.state.BaseErrorCallback
import com.jobo.commonmvvm.state.BaseLoadingCallback
import com.jobo.commonmvvm.utils.mvvmHelperLog
import com.jobo.jetpack_mvvm_demo.BuildConfig
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.commonmvvm.app.api.NetHttpClient
import com.jobo.jetpack_mvvm_demo.ui.activity.SplashActivity
import com.jobo.uicommon.ui.activity.ErrorActivity
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV
import rxhttp.RxHttpPlugins
import java.util.*

class AppTaskFactory : Project.TaskFactory(TaskCreator)

object TaskCreator : TaskCreator {
    override fun createTask(taskName: String): Task {
        return when (taskName) {
            InitNetWork.TASK_ID_INIT_NETWORK -> InitNetWork()
            InitComm.TASK_ID_INIT_COMM -> InitComm()
            InitUtils.TASK_ID_INIT_UTILS -> InitUtils()
            else -> InitDefault()
        }
    }
}

/**
 * 初始化网络
 */
class InitNetWork : Task(TASK_ID_INIT_NETWORK, true) {
    companion object {
        const val TASK_ID_INIT_NETWORK = "TASK_ID_INIT_NETWORK"
    }

    override fun run(name: String) {
        //传入自己的OKHttpClient 并添加了自己的拦截器
        val init = RxHttpPlugins.init(NetHttpClient.getDefaultOkHttpClient().build())
        init.setDebug(BuildConfig.DEBUG,true)
    }
}

/**
 * 初始化常用控件类
 */
class InitComm : Task(TASK_ID_INIT_COMM, true) {
    companion object {
        const val TASK_ID_INIT_COMM = "TASK_ID_INIT_COMM"
    }

    override fun run(name: String) {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            //设置 SmartRefreshLayout 通用配置
            layout.setEnableScrollContentWhenLoaded(true)//是否在加载完成时滚动列表显示新的内容
            layout.setFooterTriggerRate(0.6f)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            //设置 Head
            ClassicsHeader(context).apply {
                setAccentColor(getColorExt(R.color.colorBlack))
            }
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            //设置 Footer
            ClassicsFooter(context).apply {
                setAccentColor(getColorExt(R.color.colorBlack))
            }
        }
        //注册界面状态管理
        LoadSir.beginBuilder()
            .addCallback(BaseErrorCallback())
            .addCallback(BaseEmptyCallback())
            .addCallback(BaseLoadingCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
        //崩溃统一处理
        CaocConfig
            .Builder
            .create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
            .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
            .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
            .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
            .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
            .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
            .restartActivity(SplashActivity::class.java) // 重启的activity
            .errorActivity(ErrorActivity::class.java) //发生错误跳转的activity
            .apply()
        //内存泄漏

    }
}

/**
 * 初始化Utils
 */
class InitUtils : Task(TASK_ID_INIT_UTILS, true) {
    companion object {
        const val TASK_ID_INIT_UTILS = "TASK_ID_INIT_UTILS"
    }

    override fun run(name: String) {
        //初始化吐司 这个吐司必须要主线程中初始化
        ToastUtils.init(appContext)
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 100.dp)
        //初始化MMKV
        MMKV.initialize(appContext)
        //框架全局打印日志开关
        mvvmHelperLog = BuildConfig.DEBUG
    }
}

/**
 * 默认
 */
class InitDefault : Task(TASK_ID_INIT_DEFAULT, true) {
    companion object {
        const val TASK_ID_INIT_DEFAULT = "TASK_ID_INIT_DEFAULT"
    }

    override fun run(name: String) {

    }
}

/**
 * 模拟初始化SDK
 * @param millis Long
 */
fun doJob(millis: Long) {
    val nowTime = System.currentTimeMillis()
    while (System.currentTimeMillis() < nowTime + millis) {
        //程序阻塞指定时间
        val min = 10
        val max = 99
        val random = Random()
        val num = random.nextInt(max) % (max - min + 1) + min
    }
}