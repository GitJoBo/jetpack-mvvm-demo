package com.jobo.jetpack_mvvm_demo.viewModel

import com.jobo.commonmvvm.base.BaseViewModel

class WebViewModel : BaseViewModel() {
    //是否收藏
    var mCollect = false

    //收藏的Id
    var mAriticleId = 0

    //标题
    var mShowTitle: String? = null

    //文章的网络访问路径
    var mUrl: String? = null

    //需要收藏的类型 具体参数说明请看 CollectType 枚举类
    var mCollectType = 0
}