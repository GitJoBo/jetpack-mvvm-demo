package com.jobo.jetpack_mvvm_demo.app

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.Project
import com.jobo.commonmvvm.ext.currentProcessName
import com.jobo.jetpack_mvvm_demo.BuildConfig
import com.jobo.jetpack_mvvm_demo.viewModel.EventViewModel


//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { App.eventViewModelInstance }

class App : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        lateinit var instance: App
        lateinit var eventViewModelInstance: EventViewModel
//        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
        instance = this
        eventViewModelInstance = getAppViewModelProvider().get(EventViewModel::class.java)
//        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
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
    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}