package com.mozhimen.logk.test

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.logk.LogK
import com.mozhimen.logk.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.underlaykPrintLog.setOnClickListener { LogK.wk("这是一个测试") }
    }

    fun goLogK(view: View) {
        startContext<LogKActivity>()
    }
}