package com.jobo.commonmvvm.net.interception.logging.util

import android.text.TextUtils
import android.util.Log
import com.jobo.commonmvvm.utils.XLog

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/11/10 1:08 下午
 *
 */
class LogUtils private constructor() {
    companion object {
        private const val DEFAULT_TAG = "MvvmHelper"
        private var isLog = true
        fun isLog(): Boolean {
            return isLog
        }
        fun setLog(isLog: Boolean) {
            Companion.isLog = isLog
            XLog.init(isLog)
        }

        fun debugInfo(tag: String?, msg: String?) {
            if (!isLog || TextUtils.isEmpty(msg)) {
                return
            }
            Log.d(tag, msg?:"")
        }

        fun debugInfo(msg: String?) {
            debugInfo(DEFAULT_TAG, msg)
        }

        fun warnInfo(tag: String?, msg: String?) {
            if (!isLog || TextUtils.isEmpty(msg)) {
                return
            }
            Log.w(tag, msg?:"")
        }

        fun warnInfo(msg: String?) {
            warnInfo(DEFAULT_TAG, msg)
        }

        /**
         * 这里使用自己分节的方式来输出足够长度的 message
         *
         * @param tag 标签
         * @param msg 日志内容
         */
        fun debugLongInfo(tag: String?, msg: String) {
            var messgae = msg
            if (!isLog || TextUtils.isEmpty(messgae)) {
                return
            }
            messgae = messgae.trim { it <= ' ' }
            var index = 0
            val maxLength = 3500
            var sub: String
            while (index < messgae.length) {
                sub = if (messgae.length <= index + maxLength) {
                    messgae.substring(index)
                } else {
                    messgae.substring(index, index + maxLength)
                }
                index += maxLength
                Log.d(tag, sub.trim { it <= ' ' })
            }
        }

        fun debugLongInfo(msg: String) {
            debugLongInfo(DEFAULT_TAG, msg)
        }
    }

    init {
        throw IllegalStateException("you can't instantiate me!")
    }
}