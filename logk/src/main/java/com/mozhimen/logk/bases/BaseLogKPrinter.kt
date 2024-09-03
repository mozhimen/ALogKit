package com.mozhimen.logk.bases

import com.mozhimen.kotlin.elemk.cons.CMsg
import com.mozhimen.kotlin.utilk.android.util.println
import com.mozhimen.logk.commons.ILogKPrinter

/**
 * @ClassName BaseLogKPrinter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/6/26 20:14
 * @Version 1.0
 */
open class BaseLogKPrinter : ILogKPrinter {
    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        (getName() + CMsg.PART_LINE_HOR).println(priority, tag)
    }
}