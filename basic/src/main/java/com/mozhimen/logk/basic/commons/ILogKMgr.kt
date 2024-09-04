package com.mozhimen.logk.basic.commons

/**
 * @ClassName ILogKMgr
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface ILogKMgr {
    fun init(config: ILogKConfig, vararg printers: ILogKPrinter)
    /**
     * 获取配置
     */
    fun getConfig(): ILogKConfig
    /**
     * 获取打印机列表
     */
    fun getPrinters(): List<ILogKPrinter>
    /**
     * 增加打印机
     */
    fun addPrinter(printer: ILogKPrinter)
    /**
     * 移除打印机
     */
    fun removePrinter(printer: ILogKPrinter)
}