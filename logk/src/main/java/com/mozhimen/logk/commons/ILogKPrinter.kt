package com.mozhimen.logk.commons

import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.logk.bases.BaseLogKConfig

/**
 * @ClassName ILogKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
interface ILogKPrinter : IUtilK {
    fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String)

    fun getName(): String =
        NAME
}