package com.mozhimen.logk.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.logk.LogK
import com.mozhimen.logk.LogKMgr
import com.mozhimen.logk.bases.BaseLogKConfig
import com.mozhimen.logk.temps.printer.LogKPrinterMonitor
import com.mozhimen.logk.temps.printer.LogKPrinterView
import com.mozhimen.logk.test.databinding.ActivityLogkBinding

@OptIn(OApiInit_InApplication::class, OPermission_SYSTEM_ALERT_WINDOW::class)
class LogKActivity : BaseActivityVDB<ActivityLogkBinding>() {
    private val _printerView: LogKPrinterView<LogKActivity> by lazy_ofNone { LogKPrinterView(this) }
    private val _printerMonitor: LogKPrinterMonitor by lazy {
        LogKMgr.instance.getPrinters().filterIsInstance<LogKPrinterMonitor>().getOrNull(0) ?: kotlin.run {
            UtilKLogWrapper.d(TAG, "_printerMonitor: init")
            LogKPrinterMonitor().also { LogKMgr.instance.addPrinter(it) }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        initPrinterView()
        initPrinterMonitor()

        vdb.logkBtnPrint.setOnClickListener {
            printLog()
        }
        vdb.logkBtnPrinterList.setOnClickListener {
            printLog1()
        }
    }

    private fun initPrinterView() {
        _printerView.bindLifecycle(this)
        _printerView.toggleView()
    }

    private fun initPrinterMonitor() {
        //添加_printerMonitor的时候一定Application要继承BaseApplication, 因为其中实现了前后台切换的监听
        _printerMonitor.toggle()
    }

    private fun printLog() {
        //初级用法
        LogK.ik("just a test1!")

        //中级用法
        LogK.logk(CLog.W, TAG, "just a test2!")

        //高级用法
        LogK.logk(object : BaseLogKConfig() {
            override fun isIncludeThread(): Boolean =
                true

            override fun getStackTraceDepth(): Int =
                5
        }, CLog.E, TAG, "just a test3!")
    }

    private fun printLog1() {
        val printers: String = LogKMgr.instance.getPrinters().joinToString { it.getName() }
        LogK.dtk(TAG, printers)
    }

    override fun onResume() {
        super.onResume()
        LogKMgr.instance.addPrinter(_printerView)
    }
}