package com.jobo.uicommon.base

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar
import com.jobo.commonmvvm.R
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.base.BaseVmFragment
import com.jobo.uicommon.databinding.ActivityContainerBinding
import java.lang.RuntimeException
import java.lang.ref.WeakReference

/**
 * @Desc: fragment盛装容器
 * toStartContainerActivity(requireContext(), "包名.SearchFragment")
 * val intent = Intent(context, ContainerActivity::class.java)
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
 * @author: admin wsj
 * @Date: 2022/1/20 11:09 上午
 *
 */
class ContainerActivity : UIVBBaseActivity<BaseViewModel, ActivityContainerBinding>() {
    private val FRAGMENT_TAG = "content_fragment_tag"

    companion object {
        const val FRAGMENT = "fragment"
        const val BUNDLE = "bundle"
    }

    private var mFragment: WeakReference<Fragment?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, FRAGMENT_TAG)
        }
        if (fragment == null) {
            fragment = initFromIntent(intent)
        }
        val trans = supportFragmentManager
            .beginTransaction()
        trans.replace(R.id.content, fragment)
        trans.commitAllowingStateLoss()
        mFragment = WeakReference(fragment)
    }

    private fun initFromIntent(data: Intent?): Fragment {
        if (data == null) {
            throw RuntimeException(
                "you must provide a page info to display")
        }
        try {
            val fragmentName = data.getStringExtra(ContainerActivity.FRAGMENT)
            require(!(fragmentName == null || "" == fragmentName)) { "can not find page fragmentName" }
            val fragmentClass = Class.forName(fragmentName)
            val fragment = fragmentClass.newInstance() as Fragment
            val args = data.getBundleExtra(ContainerActivity.BUNDLE)
            if (args != null) {
                fragment.arguments = args
            }
            return fragment
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        throw RuntimeException("fragment initialization failed!")
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.content)
        if (fragment is BaseVmFragment<*>) {
            if (!fragment.isBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, FRAGMENT_TAG, mFragment!!.get()!!)
    }

    override fun showToolBar(): Boolean {
        ImmersionBar.with(this).init()
        return false
    }
}