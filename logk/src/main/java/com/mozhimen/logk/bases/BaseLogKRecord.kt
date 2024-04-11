package com.mozhimen.logk.bases

import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.java.util.UtilKDateWrapper
import com.mozhimen.basick.utilk.kotlin.intLogPriority2strLogPriority_ofSimple
/**
 * @ClassName LogKMo
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
open class BaseLogKRecord(var timeMillis: Long, var priority: Int, var tag: String, var msg: String) {

    open fun flattenedLog(): String =
        getFlattened() + msg

    open fun getFlattened(): String =
        "${UtilKDateWrapper.longDate2strDate(timeMillis, CDateFormat.yyyy_MM_dd_HH_mm_ss)} | Level: ${priority.intLogPriority2strLogPriority_ofSimple()} | Tag: $tag : "
}