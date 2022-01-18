package com.jobo.uicommon.databinding

import android.text.InputType
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.jobo.commonmvvm.ext.textString

/**
 * @Desc: 自定义 BindingAdapter
 * @author: admin wsj
 * @Date: 2021/11/17 6:00 下午
 *
 */
object CustomBindAdapter {
    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.textString().length)
    }
}