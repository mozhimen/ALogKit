package com.mozhimen.logk.basic.commons

/**
 * @ClassName ILogKFormatter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
interface ILogKFormatter<T> {
    fun format(data: T): String?
}