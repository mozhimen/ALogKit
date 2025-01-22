package com.mozhimen.logk.cache

import androidx.lifecycle.ProcessLifecycleOwner
import com.mozhimen.kotlin.elemk.android.util.cons.CLog
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.java.io.UtilKFileWrapper
import com.mozhimen.kotlin.utilk.java.io.file2str_use
import com.mozhimen.kotlin.utilk.java.io.getFileCreateTime
import com.mozhimen.kotlin.utilk.java.util.longMinute2longMillis
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrFile
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrPath
import com.mozhimen.kotlin.utilk.kotlin.getFolderFiles
import com.mozhimen.kotlin.utilk.kotlin.str2file_use
import com.mozhimen.logk.basic.commons.ILogKConfig
import com.mozhimen.logk.cache.commons.ILogKUploadListener
import com.mozhimen.logk.cache.db.LogKDatabase
import com.mozhimen.logk.cache.mos.LogKRecordCache
import com.mozhimen.logk.file.LogKPrinterFile
import com.mozhimen.taskk.temps.TaskKPollInfinite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName LogK2PrinterFile
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 13:59
 * @Version 1.0
 */
@OptIn(OApiCall_BindLifecycle::class, OApiInit_ByLazy::class)
class LogKPrinterCache : LogKPrinterFile {
    var crashLogPath: String? = null
        get() {
            if (field != null) return field
            val logFullPath = UtilKStrPath.Absolute.External.getCache() + "/logk_printer_file_crash"
            UtilKStrFile.createFolder(logFullPath)
            return logFullPath.also { field = it }
        }

    private var _uploadListener: ILogKUploadListener? = null

    @Volatile
    private var _isRunning = false

    @OptIn(OApiCall_BindLifecycle::class, OApiInit_ByLazy::class)
    private val _uploadTask: TaskKPollInfinite by lazy { TaskKPollInfinite().apply { bindLifecycle(ProcessLifecycleOwner.get()) } }

    /**
     * retentionMillis log文件的有效时长，单位ms，<=0表示一直有效
     */
    constructor(uploadListener: ILogKUploadListener, retentionMillis: Long) : super(retentionMillis) {
        _uploadListener = uploadListener
    }

    /**
     * retentionDay log文件的有效时长，单位天，<=0表示一直有效
     */
    constructor(uploadListener: ILogKUploadListener, retentionDay: Int) : super(retentionDay) {
        _uploadListener = uploadListener
    }

    init {
        _uploadTask.start(10L.longMinute2longMillis()) {
            withContext(Dispatchers.IO) {
                uploadCacheLog()
            }
        }
    }

    fun onCrashLog(crashLog: String) {
        saveCrashLog2File(crashLog)
    }

    override fun print(config: ILogKConfig, priority: Int, tag: String, msg: String) {
        if (_uploadListener != null) {
            val currentTimeMillis = System.currentTimeMillis()
            val uploadRes = _uploadListener!!.onUpload(currentTimeMillis, priority, tag, msg)
            if (!uploadRes)
                LogKDatabase.dao.addMLog(LogKRecordCache(currentTimeMillis, priority, tag, msg))
        }
        super.print(config, priority, tag, msg)
    }

    private fun uploadCacheLog() {
        if (_isRunning) return
        _isRunning = true
        if (_uploadListener != null) {
            val dbLogs = LogKDatabase.dao.selectMLogs()
            for (dbLog in dbLogs) {
                val uploadRes = _uploadListener!!.onUpload(dbLog.timeMillis, dbLog.priority, dbLog.tag, dbLog.msg)
                if (uploadRes)
                    LogKDatabase.dao.deleteById(dbLog.id)
            }
            val fileLogs = crashLogPath!!.getFolderFiles()
            for (fileLog in fileLogs) {
                val str = fileLog.file2str_use() ?: continue
                val uploadRes = _uploadListener!!.onUpload(fileLog.getFileCreateTime(), CLog.ERROR, TAG, str)
                if (uploadRes)
                    UtilKFileWrapper.deleteFile(fileLog)
            }
        }
        _isRunning = false
    }

    ///////////////////////////////////////////////////////////////////////////////

    private fun saveCrashLog2File(log: String) {
        val savePath = crashLogPath + "/${UtilKStrFile.getStrFileName_ofNow()/*UtilKFile.getStrFileNameForStrNowDate()*/}.txt"
        log.str2file_use(savePath)
    }
}