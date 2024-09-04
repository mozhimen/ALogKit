package com.mozhimen.logk.monitor.commons

/**
 * @ClassName ILogKPrinterMonitor
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface ILogKPrinterMonitor {
    fun isOpen(): Boolean

    //////////////////////////////////////////////////////

    fun open()
    fun open(isFold: Boolean)
    fun toggle()
    fun toggle(isFold: Boolean)
    /**
     * 关闭Monitor
     */
    fun close()
}