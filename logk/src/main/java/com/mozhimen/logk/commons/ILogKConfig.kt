package com.mozhimen.logk.commons

import com.mozhimen.basick.utilk.commons.IUtilK


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