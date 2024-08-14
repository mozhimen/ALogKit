package com.mozhimen.logk.temps.printer

import android.app.Activity
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.basick.stackk.cb.StackKCb
import com.mozhimen.basick.stackk.commons.IStackKListener
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.logk.LogK
import com.mozhimen.logk.bases.BaseLogKConfig
import com.mozhimen.logk.commons.ILogKPrinter
import com.mozhimen.logk.commons.ILogKPrinterMonitor

/**
 * @ClassName PrinterMonitor
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OPermission_SYSTEM_ALERT_WINDOW
@OApiInit_InApplication
class LogKPrinterMonitor : ILogKPrinter, ILogKPrinterMonitor, IUtilK {

    private val _logKPrinterMonitorDelegate: LogKPrinterMonitorDelegate = LogKPrinterMonitorDelegate()

    /////////////////////////////////////////////////////////////////////////////////////////

    init {
        StackKCb.instance.addFrontBackListener(object : IStackKListener {
            override fun onChanged(isFront: Boolean, activity: Activity) {
                if (!isFront && isOpen()) {
                    LogK.wtk(TAG, "PrinterMonitor onChanged log stop")
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

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        _logKPrinterMonitorDelegate.print(config, priority, tag, msg)
    }
}