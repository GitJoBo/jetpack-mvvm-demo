package com.jobo.jetpack_mvvm_demo.app

import android.app.Application
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.Project
import com.jobo.commonmvvm.ext.currentProcessName
import com.jobo.jetpack_mvvm_demo.BuildConfig

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val processName = currentProcessName
        if (processName == packageName) {
            // 主进程初始化
            onMainProcessInit()
        } else {
            // 其他进程初始化
            processName?.let { onOtherProcessInit(it) }
        }
    }

    /**
     * @description  代码的初始化请不要放在onCreate直接操作，按照下面新建异步方法
     */
    private fun onMainProcessInit() {
        AnchorsManager.getInstance()
            .debuggable(BuildConfig.DEBUG)
            //设置锚点
            .addAnchor(/*InitNetWork.TASK_ID_INIT_NETWORK,*/ InitUtils.TASK_ID_INIT_UTILS, InitComm.TASK_ID_INIT_COMM)
            .start(
                Project.Builder("app", AppTaskFactory())
                    .add(InitNetWork.TASK_ID_INIT_NETWORK)
                    .add(InitComm.TASK_ID_INIT_COMM)
                    .add(InitUtils.TASK_ID_INIT_UTILS)
                    .build()
            )
    }

    /**
     * 其他进程初始化，[processName] 进程名
     */
    private fun onOtherProcessInit(processName: String) {}
}