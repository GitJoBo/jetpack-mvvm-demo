package com.jobo.commonmvvm.net.manager

import me.hgj.jetpackmvvm.callback.livedata.event.EventLiveData

/**
 * @Desc: 网络变化管理者
 * @author: admin wsj
 * @Date: 2022/1/20 1:04 下午
 *
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = EventLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}