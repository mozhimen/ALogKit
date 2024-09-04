package com.mozhimen.logk.temps.formatter

import com.mozhimen.logk.basic.commons.ILogKFormatter

/**
 * @ClassName ThreadFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
class LogKFormatterThread : ILogKFormatter<Thread> {
    override fun format(data: Thread): String =
        "Thread: " + data.name
}