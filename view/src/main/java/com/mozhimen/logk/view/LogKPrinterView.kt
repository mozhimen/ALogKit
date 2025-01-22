package com.mozhimen.logk.view

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.utilk.android.app.getContentView
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone
import com.mozhimen.logk.basic.commons.ILogKConfig
import com.mozhimen.logk.basic.commons.ILogKMgr
import com.mozhimen.logk.basic.commons.ILogKPrinter

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
class LogKPrinterView<A>(owner: A,private var _logMgr: ILogKMgr?) : ILogKPrinter, BaseWakeBefDestroyLifecycleObserver() where A : Activity, A : LifecycleOwner {
    private val _viewProvider: LogKPrinterViewProvider by lazy_ofNone { LogKPrinterViewProvider(owner, owner.getContentView()) }
    private var _isFold = false
    private var _isShow: Boolean = false
        set(value) {
            if (value == field) return
            if (value)
                _viewProvider.showLogView(_isFold)
            else
                _viewProvider.closeLogView()
            field = value
        }

    init {
        bindLifecycle(owner)
    }

    fun toggleView(isFold: Boolean = true) {
        _isFold = isFold
        _isShow = !_isShow
    }

    fun getViewProvider(): LogKPrinterViewProvider =
        _viewProvider

    override fun print(config: ILogKConfig, priority: Int, tag: String, msg: String) {
        _viewProvider.print(config, priority, tag, msg)
    }

    override fun onPause(owner: LifecycleOwner) {
        _isShow = false
        _logMgr?.removePrinter(this)
        super.onPause(owner)
    }
}