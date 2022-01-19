package com.jobo.jetpack_mvvm_demo.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 作者　: hegaojian
 * 时间　: 2020/3/3
 * 描述　: 列表数据状态类
 */
data class ListDataUiState<T>(
    //是否请求成功
    val isSuccess: Boolean,
    //错误消息 isSuccess为false才会有
    val errMessage: String = "",
    //是否为刷新
    val isRefresh: Boolean = false,
    //是否为空
    val isEmpty: Boolean = false,
    //是否还有更多
    val hasMore: Boolean = false,
    //是第一页且没有数据
    val isFirstEmpty: Boolean = false,
    //列表数据
    val listData: ArrayList<T> = arrayListOf(),
)

/**
 * 文章
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ArticleBean(
    var apkLink: String,
    var author: String,//作者
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,//是否收藏
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: String,
    var link: String,
    var niceDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var shareUser: String,
    var tags: List<TagsBean>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int,
) : Parcelable

/**
 * 文章的标签
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class TagsBean(var name: String, var url: String) : Parcelable

/**
 * 项目分类
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class ClassifyBean(
    var children: List<String> = listOf(),
    var courseId: Int = 0,
    var id: Int = 0,
    var name: String = "",
    var order: Int = 0,
    var parentChapterId: Int = 0,
    var userControlSetTop: Boolean = false,
    var visible: Int = 0,
) : Parcelable

/**
 *  体系数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class SystemBean(
    var children: ArrayList<ClassifyBean>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int,
) : Parcelable

/**
 * 导航数据
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class NavigationBean(
    var articles: ArrayList<ArticleBean>,
    var cid: Int,
    var name: String,
) : Parcelable

/**
 * 收藏数据状态类
 */
data class CollectUiState(
    //请求是否成功
    var isSuccess: Boolean = true,
    //收藏
    var collect: Boolean = false,
    //收藏Id
    var id: String = "-1",
    //请求失败的错误信息
    var errorMsg: String? = ""
)

/**
 * 积分
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class IntegralBean(
    var coinCount: String,//当前积分
    var rank: String,
    var userId: String,
    var username: String) : Parcelable

/**
 * 积分记录
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class IntegralHistoryBean(
    var coinCount: Int,
    var date: Long,
    var desc: String,
    var id: Int,
    var type: Int,
    var reason: String,
    var userId: Int,
    var userName: String) : Parcelable
