package com.mozhimen.logk.temps.formatter

import com.mozhimen.logk.commons.ILogKFormatter

/**
 * @ClassName ThreadFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:43
 * @Version 1.0
 */
class LogKFormatterThread : ILogKFormatter<Thread> {
    override fun format(data: Thread): String =
        "Thread: " + data.name
}