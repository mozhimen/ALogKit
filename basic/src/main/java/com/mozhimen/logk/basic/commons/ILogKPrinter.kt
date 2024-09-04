package com.mozhimen.logk.basic.commons

import com.mozhimen.kotlin.utilk.commons.IUtilK

/**
 * @ClassName ILogKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
interface ILogKPrinter : IUtilK {
    fun print(config: ILogKConfig, priority: Int, tag: String, msg: String)

    fun getName(): String =
        NAME
}