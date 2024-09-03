package com.mozhimen.logk.commons

import com.mozhimen.kotlin.elemk.android.util.annors.ALog
import com.mozhimen.logk.bases.BaseLogKConfig


/**
 * @ClassName ILogK
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 18:07
 * @Version 1.0
 */
interface ILogK {

    fun vk(vararg contents: Any)
    fun vtk(tag: String, vararg contents: Any)
    fun dk(vararg contents: Any)
    fun dtk(tag: String, vararg contents: Any)
    fun ik(vararg contents: Any)
    fun itk(tag: String, vararg contents: Any)
    fun wk(vararg contents: Any)
    fun wtk(tag: String, vararg contents: Any)
    fun ek(vararg contents: Any)
    fun etk(tag: String, vararg contents: Any)
    fun ak(vararg contents: Any)
    fun atk(tag: String, vararg contents: Any)
    fun logk(@ALog priority: Int, vararg contents: Any)
    fun logk(@ALog priority: Int, tag: String, vararg contents: Any)
    /**
     * log实现
     * @param config LogKConfig
     * @param priority Int
     * @param tag String
     * @param contents Array<out Any?>
     */
    fun logk(config: BaseLogKConfig, @ALog priority: Int, tag: String, vararg contents: Any?)
}