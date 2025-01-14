package com.mozhimen.logk.basic.commons


/**
 * @ClassName BaseMLogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 18:00
 * @Version 1.0
 */
interface ILogKRecord {
    var priority: Int
    var tag: String
    var msg: String
    fun flattenedLog(): String
    fun getFlattened(): String
}