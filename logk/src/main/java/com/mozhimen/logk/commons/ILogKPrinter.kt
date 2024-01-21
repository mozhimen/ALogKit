package com.mozhimen.logk.commons

import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.logk.bases.BaseLogKConfig

/**
 * @ClassName ILogKPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:35
 * @Version 1.0
 */
interface ILogKPrinter : IUtilK {
    fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String)

    fun getName(): String =
        NAME
}