package com.mozhimen.logk.helpers

import com.mozhimen.basick.elemk.android.util.annors.ALog
import com.mozhimen.basick.elemk.android.util.cons.CLog
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.getStrPackage
import com.mozhimen.logk.LogK
import com.mozhimen.logk.LogKMgr
import com.mozhimen.logk.bases.BaseLogKConfig
import com.mozhimen.logk.commons.ILogK
import com.mozhimen.logk.commons.ILogKPrinter
import com.mozhimen.logk.temps.formatter.LogKFormatterStackTraces
import com.mozhimen.logk.temps.formatter.LogKFormatterThread
import com.mozhimen.logk.utils.StackTraceElementUtil


/**
 * @ClassName LogKProvider
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/10 18:06
 * @Version 1.0
 */
@OptIn(OApiInit_InApplication::class)
class LogKProvider : ILogK, IUtilK {
    private val _logKPackageName: String by lazy { LogK::class.java.getStrPackage() }
    private val _logKFormatterThread: LogKFormatterThread by lazy { LogKFormatterThread() }
    private val _logKFormatterStackTraces: LogKFormatterStackTraces by lazy { LogKFormatterStackTraces() }

    override fun vk(vararg contents: Any) {
        logk(CLog.VERBOSE, *contents)
    }

    override fun vtk(tag: String, vararg contents: Any) {
        logk(CLog.VERBOSE, tag, *contents)
    }

    override fun dk(vararg contents: Any) {
        logk(CLog.DEBUG, *contents)
    }

    override fun dtk(tag: String, vararg contents: Any) {
        logk(CLog.DEBUG, tag, *contents)
    }

    override fun ik(vararg contents: Any) {
        logk(CLog.INFO, *contents)
    }

    override fun itk(tag: String, vararg contents: Any) {
        logk(CLog.INFO, tag, *contents)
    }

    override fun wk(vararg contents: Any) {
        logk(CLog.WARN, *contents)
    }

    override fun wtk(tag: String, vararg contents: Any) {
        logk(CLog.WARN, tag, *contents)
    }

    override fun ek(vararg contents: Any) {
        logk(CLog.ERROR, *contents)
    }

    override fun etk(tag: String, vararg contents: Any) {
        logk(CLog.ERROR, tag, *contents)
    }

    override fun ak(vararg contents: Any) {
        logk(CLog.ASSERT, *contents)
    }

    override fun atk(tag: String, vararg contents: Any) {
        logk(CLog.ASSERT, tag, *contents)
    }

    override fun logk(@ALog priority: Int, vararg contents: Any) {
        logk(priority, LogKMgr.instance.getConfig().getGlobalTag(), contents)
    }

    override fun logk(@ALog priority: Int, tag: String, vararg contents: Any) {
        logk(LogKMgr.instance.getConfig(), priority, tag, *contents)
    }

    override fun logk(config: BaseLogKConfig, @ALog priority: Int, tag: String, vararg contents: Any?) {
        if (!config.isEnable()) return

        val stringBuilder = StringBuilder()
        if (config.isIncludeThread()) {
            val threadInfo: String = _logKFormatterThread.format(Thread.currentThread())
            stringBuilder.append(threadInfo).append("\n")
        }
        if (config.getStackTraceDepth() > 0 || (config.getStackTraceDepth() <= 0 && priority >= CLog.ERROR)) {
            val stackTrace: String? = _logKFormatterStackTraces.format(
                StackTraceElementUtil.gets_ofRealCropped(
                    Throwable().stackTrace, _logKPackageName,
                    if (config.getStackTraceDepth() <= 0) 5
                    else config.getStackTraceDepth()
                )
            )
            stringBuilder.append(stackTrace).append("\n")
        }

        require(contents.isNotEmpty()) { "$TAG content's size must not be 0" }

        val body = parseBody(contents, config)
        stringBuilder.append(body)
        val printers: MutableList<ILogKPrinter> = ArrayList()
        if (config.getPrinters() != null)
            printers.addAll(config.getPrinters()!!)
        else
            printers.addAll(LogKMgr.instance.getPrinters())

        if (printers.isEmpty()) return

        //打印log
        for (printer in printers)
            printer.print(config, priority, tag, stringBuilder.toString().replace("[", "").replace("]", ""))
    }

    private fun parseBody(contents: Array<out Any?>, config: BaseLogKConfig): String {
        if (config.injectJsonParser() != null)
            return config.injectJsonParser()!!.toJson(contents)

        val stringBuilder = StringBuilder()
        for (content in contents)
            stringBuilder.append(content.toString()).append(";")

        if (stringBuilder.isNotEmpty())
            stringBuilder.deleteCharAt(stringBuilder.length - 1)
        return stringBuilder.toString()
    }
}