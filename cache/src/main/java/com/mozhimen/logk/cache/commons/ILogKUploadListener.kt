package com.mozhimen.logk.cache.commons


/**
 * @ClassName IUploadListener
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 14:33
 * @Version 1.0
 */
interface ILogKUploadListener {
    fun onUpload(timeMillis: Long, priority: Int, tag: String, msg: String): Boolean
}