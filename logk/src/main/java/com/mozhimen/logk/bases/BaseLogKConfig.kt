package com.mozhimen.logk.bases

import com.mozhimen.logk.commons.ILogKConfig
import com.mozhimen.logk.commons.ILogKJsonParser
import com.mozhimen.logk.commons.ILogKPrinter
import com.mozhimen.serialk.moshi.t2strJson_ofMoshi

/**
 * @ClassName LogKConfig
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
open class BaseLogKConfig : ILogKConfig {
    override fun injectJsonParser(): ILogKJsonParser? =
        object : ILogKJsonParser {
            override fun toJson(src: Any): String =
                src.t2strJson_ofMoshi()
        }

    override fun getGlobalTag(): String =
        TAG

    override fun getStackTraceDepth(): Int =
        0

    override fun getPrinters(): Array<ILogKPrinter>? =
        null

    override fun isEnable(): Boolean =
        true

    override fun isIncludeThread(): Boolean =
        false
}