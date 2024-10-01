package com.mozhimen.logk.test

import android.os.Bundle
import android.view.View
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.logk.LogK
import com.mozhimen.logk.test.databinding.ActivityMainBinding
import com.mozhimen.bindk.bases.activity.databinding.BaseActivityVDB

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.underlaykPrintLog.setOnClickListener { LogK.wk("这是一个测试") }
    }

    fun goLogK(view: View) {
        startContext<LogKActivity>()
    }
}