package com.mozhimen.logk.test

import com.mozhimen.kotlin.elemk.android.app.bases.BaseApplication
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.OApiMultiDex_InApplication
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.serialk.moshi.t2strJson_ofMoshi
import com.mozhimen.logk.LogKMgr
import com.mozhimen.logk.bases.BaseLogKConfig
import com.mozhimen.logk.commons.ILogKJsonParser
import com.mozhimen.logk.temps.printer.LogKPrinterConsole
import com.mozhimen.logk.temps.printer.LogKPrinterFile
import com.mozhimen.logk.temps.printer.LogKPrinterMonitor

/**
 * @ClassName MainApplication
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/1/21 14:57
 * @Version 1.0
 */
@OptIn(OApiMultiDex_InApplication::class, OPermission_SYSTEM_ALERT_WINDOW::class)
class MainApplication : BaseApplication() {
    @OptIn(OApiInit_InApplication::class)
    override fun onCreate() {
        super.onCreate()

        //logk
        LogKMgr.instance.init(_logkConfig, LogKPrinterConsole(), LogKPrinterFile(retentionDay = 3), LogKPrinterMonitor())
    }

    private val _logkConfig = object : BaseLogKConfig() {
        override fun injectJsonParser(): ILogKJsonParser =
            object : ILogKJsonParser {
                override fun toJson(src: Any): String =
                    src.t2strJson_ofMoshi()
            }

        override fun getGlobalTag(): String =
            TAG

        override fun getStackTraceDepth(): Int =
            0

        override fun isEnable(): Boolean =
            true

        override fun isIncludeThread(): Boolean =
            false
    }
}