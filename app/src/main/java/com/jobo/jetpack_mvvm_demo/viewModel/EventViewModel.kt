package com.jobo.jetpack_mvvm_demo.viewModel

import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.jetpack_mvvm_demo.data.model.event.CollectBus
import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 * @Desc: APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 * @author: admin wsj
 * @Date: 2022/1/18 5:30 下午
 *
 */
class EventViewModel : BaseViewModel() {

    //全局收藏，在任意一个地方收藏或取消收藏，监听该值的界面都会收到消息
    val collectEvent = EventLiveData<CollectBus>()

    //分享文章通知
    val shareArticleEvent = EventLiveData<Boolean>()

    //添加TODO通知
    val todoEvent = EventLiveData<Boolean>()

}