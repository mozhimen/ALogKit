package com.mozhimen.logk.basic.bases

import com.mozhimen.kotlin.elemk.java.util.cons.CDateFormat
import com.mozhimen.kotlin.utilk.java.text.UtilKSimpleDateFormatFormat
import com.mozhimen.kotlin.utilk.kotlin.intLogPriority2strLogPriority_ofSimple
import com.mozhimen.logk.basic.commons.ILogKRecord

/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
open class BaseLogKRecord(var timeMillis: Long, var priority: Int, var tag: String, var msg: String) : ILogKRecord {

    override fun getPriority(): Int {
        return priority
    }

    override fun getTag(): String {
        return tag
    }

    override fun getMsg(): String {
        return msg
    }

    override fun flattenedLog(): String =
        getFlattened() + msg

    override fun getFlattened(): String =
        "${UtilKSimpleDateFormatFormat.longDate2strDate(timeMillis, CDateFormat.Format.yyyy_MM_dd_HH_mm_ss)} | Level: ${priority.intLogPriority2strLogPriority_ofSimple()} | Tag: $tag : "
}