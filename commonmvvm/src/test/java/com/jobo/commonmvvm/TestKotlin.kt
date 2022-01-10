package com.jobo.commonmvvm

import org.junit.Test

class TestKotlin {

    private val ss by lazy {
        println("初始化by lazy")
        "测试"
    }

    @Test
    fun main() {
        testByLazy()
    }

    /**
     * 打印结果：
    初始化by lazy
    测试
    ===============分割线===============
    测试
     */
    private fun testByLazy() {
        println(ss)
        println("===============分割线===============")
        println(ss)
    }
}