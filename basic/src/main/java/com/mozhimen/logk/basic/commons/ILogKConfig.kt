package com.mozhimen.logk.basic.commons

import com.mozhimen.kotlin.utilk.commons.IUtilK


/**
 * @ClassName ILogKConfig
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface ILogKConfig : IUtilK {
    fun injectJsonParser(): ILogKJsonParser?
    fun getGlobalTag(): String
    fun getPrinters(): Array<ILogKPrinter>?
    fun getStackTraceDepth(): Int

    ////////////////////////////////////////////////////////

    fun isEnable(): Boolean
    fun isIncludeThread(): Boolean
}