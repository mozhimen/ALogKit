package com.mozhimen.logk.test

import com.mozhimen.basick.elemk.android.app.bases.BaseApplication
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.OApiMultiDex_InApplication
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.squareup.moshi.t2strJsonMoshi
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
@OptIn(OApiMultiDex_InApplication::class)
@AManifestKRequire(CPermission.SYSTEM_ALERT_WINDOW)
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
                    src.t2strJsonMoshi()
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