package com.mozhimen.logk.basic.commons


/**
 * @ClassName ILogKJsonParser
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface ILogKJsonParser {
    fun toJson(src: Any): String
}