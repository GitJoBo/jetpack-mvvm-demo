package com.jobo.commonmvvm.net

/**
 * @Desc:
 * @author: admin wsj
 * @Date: 2021/11/10 11:56 上午
 *
 */
data class LoadingDialogEntity(
    @LoadingType var loadingType: Int = LoadingType.LOADING_NULL,
    var loadingMessage: String = "",
    var isShow: Boolean = false,
    var requestCode: String = "mmp"
)