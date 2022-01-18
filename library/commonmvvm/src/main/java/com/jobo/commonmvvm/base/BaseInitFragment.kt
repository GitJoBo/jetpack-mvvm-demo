package com.jobo.commonmvvm.base

import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseInitFragment : Fragment() {
    abstract val layoutId: Int
    var dataBindView: View? = null
}