package com.jobo.commonmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import com.noober.background.BackgroundLibrary
import java.lang.reflect.ParameterizedType

/**
 * @Desc: activity基类，使用ViewDataBinding
 * @author: admin wsj
 * @Date: 2021/11/17 5:17 下午
 *
 */
abstract class BaseDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>(), BaseIView {
    //使用了DataBinding 就不需要 layoutId了，因为 会从DB泛型 找到相关的view
    override val layoutId: Int = 0

    lateinit var mBind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        initDataBind()
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建DataBinding
     */
    private fun initDataBind() {
        //利用反射 根据泛型得到 ViewDataBinding
        val superClass = javaClass.genericSuperclass
        val aClass = (superClass as ParameterizedType).actualTypeArguments[1] as Class<*>
        BackgroundLibrary.inject(this)
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBind = method.invoke(null, layoutInflater) as DB
        dataBindView = mBind.root
        mBind.lifecycleOwner = this
    }
}