package me.hgj.jetpackmvvm.demo.app.weight.loadCallBack


import com.jobo.jetpack_mvvm_demo.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}