package com.jobo.commonmvvm

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import org.junit.Test

class TestKotlin {

    private val ss by lazy {
        println("初始化by lazy")
        "测试"
    }

    @Test
    fun main() {
//        testByLazy()

//        runBlocking {
////            ctripTest()
////            coroutineThreadSchedulerTest()
//            coroutineThreadSchedulerWithContextTest()
//        }


    }


    /**
     * 携程测试
     * 打印结果：
    ctripTest() Thread1 name is Test worker;id is 13
    runBlocking Thread2 name is Test worker @coroutine#1;id is 13
    GlobalScope.launch Thread3 name is DefaultDispatcher-worker-1 @coroutine#2;id is 16
    GlobalScope.async Thread5 name is DefaultDispatcher-worker-2 @coroutine#3;id is 17
    runBlocking2 Thread4 name is DefaultDispatcher-worker-1 @coroutine#4;id is 16
    总结：runBlocking线程不变
    GlobalScope.launch 新建线程
    GlobalScope.async 新建线程
    async 和 launch 还是有区别的，async 可以有返回值，通过它的 await 方法进行获取，需要注意的是这个方法只能在协程的操作符或者被 suspend 修饰的方法中才能调用。
     */
    private suspend fun ctripTest() {
        println("ctripTest() Thread1 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
        runBlocking {
            println("runBlocking Thread2 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
        }
        GlobalScope.launch {
            println("GlobalScope.launch Thread3 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
            runBlocking {
                println("runBlocking2 Thread4 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
            }
        }
        val async = GlobalScope.async {
            println("GlobalScope.async Thread5 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
            return@async "async 可以有返回值"
        }
        println("async 可以有返回值：${async.await()}")
    }

    /**
     * 携程测试-协程的线程调度器
     * 打印结果
    coroutineThreadSchedulerTest() Thread1 name is Test worker @coroutine#1;id is 13
    测试结束
    测试延迟开始
    GlobalScope.launch Thread3 name is DefaultDispatcher-worker-1 @coroutine#2;id is 16
    测试结束2
    测试延迟结束
     */
    suspend fun coroutineThreadSchedulerTest() {
        println("coroutineThreadSchedulerTest() Thread1 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
        //Dispatchers.Main：主线程调度器，人如其名，会在主线程中执行
        //Dispatchers.IO：工作线程调度器，人如其名，会在子线程中执行
        //Dispatchers.Default：默认调度器，没有设置调度器时就用这个，经过测试效果基本等同于 Dispatchers.IO
        //Dispatchers.Unconfined：无指定调度器，根据当前执行的环境而定，会在当前的线程上执行，另外有一点需要注意，由于是直接拿当前线程执行，经过实践，协程块中的代码执行过程中不会有延迟，会被立马执行，除非遇到需要协程被挂起了，才会去执行协程外的代码，这个也是跟其他类型的调度器不相同的地方
        GlobalScope.launch(Dispatchers.IO) {
            println("测试延迟开始")
            println("GlobalScope.launch Thread3 name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
            delay(2000)
            println("测试延迟结束")
        }
        println("测试结束")
        delay(2000)
        println("测试结束2")
    }

    /**
     * 携程测试-协程的线程调度器-withContext
     * 打印结果：
    launch开始
    测试结束
    测试延迟开始
    GlobalScope.launch Thread name is DefaultDispatcher-worker-2 @coroutine#2;id is 17
    测试延迟结束
    launch结束
    测试结束2
     */
    suspend fun coroutineThreadSchedulerWithContextTest() {
        GlobalScope.launch {
            println("launch开始")
            withContext(Dispatchers.IO) {
                println("测试延迟开始")
                println("GlobalScope.launch Thread name is ${Thread.currentThread().name};id is ${Thread.currentThread().id}")
                delay(2000)
                println("测试延迟结束")
            }
            println("launch结束")
        }
        println("测试结束")
        delay(4000)
        println("测试结束2")
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