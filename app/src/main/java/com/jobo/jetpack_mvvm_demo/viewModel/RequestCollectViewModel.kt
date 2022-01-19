package com.jobo.jetpack_mvvm_demo.viewModel

import androidx.lifecycle.MutableLiveData
import com.jobo.commonmvvm.base.BaseViewModel
import com.jobo.commonmvvm.ext.rxHttpRequest
import com.jobo.jetpack_mvvm_demo.data.model.bean.CollectUiState
import com.jobo.jetpack_mvvm_demo.data.repository.UserRepository

/**
 * @Desc: 收藏
 * @author: admin wsj
 * @Date: 2022/1/18 4:44 下午
 *
 */
class RequestCollectViewModel : BaseViewModel() {
    /**
     * 收藏文章
     */
    val favoriteArticles = MutableLiveData<CollectUiState>()

    /**
     * 收藏 文章
     * 提醒一下，玩安卓的收藏 和取消收藏 成功后返回的data值为null，所以在CollectRepository中的返回值一定要加上 非空？
     * 不然会出错
     */
    fun favoriteArticles(id: String) {
        rxHttpRequest {
            onRequest = {
                val await = UserRepository.postFavoriteArticles(id).await()
                val uiState = CollectUiState(isSuccess = true, collect = true, id = id)
                favoriteArticles.value = uiState
            }
            onError = {
                val uiState = CollectUiState(isSuccess = false, collect = false, errorMsg = it.message, id = id)
                favoriteArticles.value = uiState
            }
        }
    }

    /**
     * 取消收藏
     * @param id String
     */
    fun unfavorite(id: String) {
        rxHttpRequest {
            onRequest = {
                val await = UserRepository.postUnfavorite(id).await()
                val uiState = CollectUiState(isSuccess = true, collect = false, id = id)
                favoriteArticles.value = uiState
            }

            onError = {
                val uiState =
                    CollectUiState(isSuccess = false, collect = false, errorMsg = it.message, id = id)
                favoriteArticles.value = uiState
            }
        }
    }

}