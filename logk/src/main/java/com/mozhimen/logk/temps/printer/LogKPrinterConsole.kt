package com.mozhimen.logk.temps.printer

import com.mozhimen.kotlin.elemk.cons.CMsg
import com.mozhimen.kotlin.utilk.android.util.println
import com.mozhimen.logk.bases.BaseLogKConfig
import com.mozhimen.logk.bases.BaseLogKPrinter
import com.mozhimen.logk.basic.commons.ILogKConfig
import com.mozhimen.logk.basic.cons.CLogKCons

/**
 * @ClassName ConsolePrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
class LogKPrinterConsole(private val _ignoreLineBreak: Boolean = false) : BaseLogKPrinter() {

    override fun print(config: ILogKConfig, priority: Int, tag: String, msg: String) {
        super.print(config, priority, tag, msg)

        if (_ignoreLineBreak) {
            val length = msg.length
            val countOfSub = length / com.mozhimen.logk.basic.cons.CLogKCons.LOG_MAX_LEN
            if (countOfSub > 0) {
                var index = 0
                for (i in 0 until countOfSub) {
                    printlog(priority, tag, msg.substring(index, index + com.mozhimen.logk.basic.cons.CLogKCons.LOG_MAX_LEN))
                    index += com.mozhimen.logk.basic.cons.CLogKCons.LOG_MAX_LEN
                }
                if (index != length) printlog(priority, tag, msg.substring(index, length))
            } else
                printlog(priority, tag, msg)
        } else {
            val msgs: MutableSet<String> = msg.split(CMsg.LINE_BREAK).toMutableSet()
            val lines: List<String>? = msgs.lastOrNull()?.replace(CMsg.BLANK_STR, CMsg.TAB_STR)?.split(CMsg.LINE_BREAK_STR)
            if (!lines.isNullOrEmpty())
                msgs.addAll(msgs)
            for (m in msgs)
                printlog(priority, tag, m)
        }

        (getName() + CMsg.PART_LINE_HOR).println(priority, tag)
    }

    private fun printlog(level: Int, tag: String, msg: String) {
        msg.println(level, tag)
    }
}