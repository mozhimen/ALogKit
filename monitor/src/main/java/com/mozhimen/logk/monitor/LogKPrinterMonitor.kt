package com.mozhimen.logk.monitor

import android.app.Activity
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.logk.basic.commons.ILogK
import com.mozhimen.logk.basic.commons.ILogKConfig
import com.mozhimen.logk.basic.commons.ILogKPrinter
import com.mozhimen.logk.monitor.commons.ILogKPrinterMonitor
import com.mozhimen.stackk.basic.commons.IStackKListener
import com.mozhimen.stackk.callback.StackKCb

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OPermission_SYSTEM_ALERT_WINDOW
@OApiInit_InApplication
class LogKPrinterMonitor(private val _logk: ILogK) : ILogKPrinter, ILogKPrinterMonitor, IUtilK {

    private val _logKPrinterMonitorDelegate: LogKPrinterMonitorDelegate = LogKPrinterMonitorDelegate(_logk)

    /////////////////////////////////////////////////////////////////////////////////////////

    init {
        StackKCb.instance.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean, activity: Activity) {
                if (!isFront && isOpen()) {
                    _logk.wtk(TAG, "PrinterMonitor onChanged log stop")
                    _logKPrinterMonitorDelegate.close()
                }
//                if (isFront) {
//                    if (_isShow) {
//                        LogK.d(TAG, "PrinterMonitor onChanged log start")
//                        _printerMonitorProvider.openMonitor(true)
//                    }
//                }
            }
        })
    }

    override fun isOpen(): Boolean =
        _logKPrinterMonitorDelegate.isOpen()

    override fun open() {
        _logKPrinterMonitorDelegate.open()
    }

    override fun open(isFold: Boolean) {
        _logKPrinterMonitorDelegate.open(isFold)
    }

    override fun toggle() {
        _logKPrinterMonitorDelegate.toggle()
    }

    override fun toggle(isFold: Boolean) {
        _logKPrinterMonitorDelegate.toggle(isFold)
    }

    override fun close() {
        _logKPrinterMonitorDelegate.close()
    }

    override fun print(config: ILogKConfig, priority: Int, tag: String, msg: String) {
        _logKPrinterMonitorDelegate.print(config, priority, tag, msg)
    }
}