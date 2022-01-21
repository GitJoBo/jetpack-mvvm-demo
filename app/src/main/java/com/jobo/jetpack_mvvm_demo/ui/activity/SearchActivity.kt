package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.jetpack_mvvm_demo.R
import com.jobo.jetpack_mvvm_demo.databinding.ActivitySearchBinding
import com.jobo.uicommon.base.UIVBBaseActivity

/**
 * @Desc: 搜索 + 单Activity演示
 * @author: admin wsj
 * @Date: 2022/1/20 11:13 上午
 *
 */
//class SearchActivity : UIVBBaseActivity<BaseViewModel, ActivitySearchBinding>() {
//    override fun initView(savedInstanceState: Bundle?) {
//
//    }
//
//    override fun showToolBar(): Boolean {
//        ImmersionBar.with(this).init()
//        return false
//    }
//}
class SearchActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}