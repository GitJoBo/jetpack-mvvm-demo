package com.jobo.jetpack_mvvm_demo.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.jobo.commonmvvm.data.annotation.ValueKey
import com.jobo.commonmvvm.ext.*
import com.jobo.jetpack_mvvm_demo.data.model.bean.IntegralBean
import com.jobo.jetpack_mvvm_demo.databinding.ActivityIntegralBinding
import com.jobo.jetpack_mvvm_demo.ui.adapter.IntegralAdapter
import com.jobo.jetpack_mvvm_demo.ui.weight.recyclerview.SpaceItemDecoration
import com.jobo.jetpack_mvvm_demo.viewModel.IntegralViewModel
import com.jobo.uicommon.base.UIVBBaseActivity

/**
 * @Desc: 积分
 * @author: admin wsj
 * @Date: 2022/1/19 2:31 下午
 *
 */
class IntegralActivity : UIVBBaseActivity<IntegralViewModel, ActivityIntegralBinding>() {
    private var rank: IntegralBean? = null
    lateinit var mIntegralAdapter: IntegralAdapter
//    val ss =
//        "{\"data\":{\"curPage\":0,\"datas\":[{\"coinCount\":62396,\"level\":624,\"nickname\":\"\",\"rank\":\"-29\",\"userId\":20382,\"username\":\"g**eii\"},{\"coinCount\":61093,\"level\":611,\"nickname\":\"\",\"rank\":\"-28\",\"userId\":29303,\"username\":\"深**士\"},{\"coinCount\":45798,\"level\":458,\"nickname\":\"\",\"rank\":\"-27\",\"userId\":2,\"username\":\"x**oyang\"},{\"coinCount\":32471,\"level\":325,\"nickname\":\"\",\"rank\":\"-26\",\"userId\":7809,\"username\":\"1**23822235\"},{\"coinCount\":32421,\"level\":325,\"nickname\":\"\",\"rank\":\"-25\",\"userId\":3559,\"username\":\"A**ilEyon\"},{\"coinCount\":32183,\"level\":322,\"nickname\":\"\",\"rank\":\"-24\",\"userId\":14829,\"username\":\"l**changwen\"},{\"coinCount\":31941,\"level\":320,\"nickname\":\"\",\"rank\":\"-23\",\"userId\":7891,\"username\":\"h**zkp\"},{\"coinCount\":31269,\"level\":313,\"nickname\":\"\",\"rank\":\"-22\",\"userId\":7710,\"username\":\"i**Cola7\"},{\"coinCount\":31087,\"level\":311,\"nickname\":\"\",\"rank\":\"-21\",\"userId\":4886,\"username\":\"z**iyun\"},{\"coinCount\":30934,\"level\":310,\"nickname\":\"\",\"rank\":\"-20\",\"userId\":26707,\"username\":\"p**xc.com\"},{\"coinCount\":30292,\"level\":303,\"nickname\":\"\",\"rank\":\"-19\",\"userId\":2068,\"username\":\"i**Cola\"},{\"coinCount\":29857,\"level\":299,\"nickname\":\"\",\"rank\":\"-18\",\"userId\":29390,\"username\":\"L**ing\"},{\"coinCount\":29791,\"level\":298,\"nickname\":\"\",\"rank\":\"-17\",\"userId\":12351,\"username\":\"w**igeny\"},{\"coinCount\":29529,\"level\":296,\"nickname\":\"\",\"rank\":\"-16\",\"userId\":833,\"username\":\"w**lwaywang6\"},{\"coinCount\":29465,\"level\":295,\"nickname\":\"\",\"rank\":\"-15\",\"userId\":28457,\"username\":\"w**dla\"},{\"coinCount\":29301,\"level\":294,\"nickname\":\"\",\"rank\":\"-14\",\"userId\":1534,\"username\":\"j**gbin\"},{\"coinCount\":29275,\"level\":293,\"nickname\":\"\",\"rank\":\"-13\",\"userId\":7590,\"username\":\"陈**啦啦啦\"},{\"coinCount\":29155,\"level\":292,\"nickname\":\"\",\"rank\":\"-12\",\"userId\":25419,\"username\":\"蔡**打篮球\"},{\"coinCount\":29091,\"level\":291,\"nickname\":\"\",\"rank\":\"-11\",\"userId\":3825,\"username\":\"请**娃哈哈\"},{\"coinCount\":29091,\"level\":291,\"nickname\":\"\",\"rank\":\"-10\",\"userId\":25128,\"username\":\"f**wandroid\"},{\"coinCount\":28211,\"level\":283,\"nickname\":\"\",\"rank\":\"-9\",\"userId\":2160,\"username\":\"R**iner\"},{\"coinCount\":27189,\"level\":272,\"nickname\":\"\",\"rank\":\"-8\",\"userId\":14839,\"username\":\"x**y_sjc\"},{\"coinCount\":26535,\"level\":266,\"nickname\":\"\",\"rank\":\"-7\",\"userId\":25793,\"username\":\"F**_2014\"},{\"coinCount\":25948,\"level\":260,\"nickname\":\"\",\"rank\":\"-6\",\"userId\":2657,\"username\":\"a**111993@163.com\"},{\"coinCount\":25878,\"level\":259,\"nickname\":\"\",\"rank\":\"-5\",\"userId\":29185,\"username\":\"轻**宇\"},{\"coinCount\":25641,\"level\":257,\"nickname\":\"\",\"rank\":\"-4\",\"userId\":29398,\"username\":\"l**bwstory\"},{\"coinCount\":25507,\"level\":256,\"nickname\":\"\",\"rank\":\"-3\",\"userId\":42260,\"username\":\"C**nY\"},{\"coinCount\":25376,\"level\":254,\"nickname\":\"\",\"rank\":\"-2\",\"userId\":13273,\"username\":\"1**17315362\"},{\"coinCount\":25192,\"level\":252,\"nickname\":\"\",\"rank\":\"-1\",\"userId\":20592,\"username\":\"c**hao9808\"},{\"coinCount\":24828,\"level\":249,\"nickname\":\"\",\"rank\":\"0\",\"userId\":1580,\"username\":\"k**od21\"}],\"offset\":-30,\"over\":false,\"pageCount\":3270,\"size\":30,\"total\":98093},\"errorCode\":0,\"errorMsg\":\"\"}"

    override fun initView(savedInstanceState: Bundle?) {
        rank = intent?.getParcelableExtra(ValueKey.KEY)
        rank.notNull({
            mBind.tvIntegralMerank.text = rank!!.rank
            mBind.tvIntegralMecount.text = rank!!.coinCount
            mBind.tvIntegralMename.text = rank!!.username
        }, {
            mBind.cd.gone()
        })
        mIntegralAdapter = IntegralAdapter(arrayListOf(), rank?.rank?.toInt() ?: -1)
        mBind.includedSmartRefreshRv.recyclerView.run {
            addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            layoutManager = LinearLayoutManager(this@IntegralActivity)
            adapter = mIntegralAdapter
        }
        mToolbar.title = "积分排行"
        mBind.includedSmartRefreshRv.smartRefreshLayout
            .refresh {
                mViewModel.getIntegralRank(true)
            }
            .loadMore {
                mViewModel.getIntegralRank()
            }
        mViewModel.getIntegralRank(true, showLoadXml = true)
//        val fromJson = GsonUtils.fromJson<ApiResponse<ApiPagerResponse<IntegralBean>>>(ss,
//            object : TypeToken<ApiResponse<ApiPagerResponse<IntegralBean>>>() {}.type)
//        (fromJson.data.datas[0] as IntegralBean).coinCount.logD()

    }

    override fun onRequestSuccess() {
        mViewModel.integralRank.observe(this, {
            mIntegralAdapter.loadListSuccess(it, mBind.includedSmartRefreshRv.smartRefreshLayout)
        })
    }
}