package com.jobo.commonmvvm.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseInitActivity : AppCompatActivity() {
    abstract val layoutId: Int
    var dataBindView: View? = null
}