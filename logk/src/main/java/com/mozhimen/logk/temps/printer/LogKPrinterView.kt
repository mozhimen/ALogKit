package com.mozhimen.logk.temps.printer

import android.app.Activity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optins.OApiInit_InApplication
import com.mozhimen.basick.utilk.android.app.getContentView
import com.mozhimen.basick.utilk.kotlin.UtilKLazyJVM.lazy_ofNone
import com.mozhimen.logk.LogKMgr
import com.mozhimen.logk.commons.ILogKPrinter
import com.mozhimen.logk.bases.BaseLogKConfig

/**
 * @ClassName ViewPrinter
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
@OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class, OApiInit_InApplication::class)
class LogKPrinterView<A>(owner: A) : ILogKPrinter, BaseWakeBefPauseLifecycleObserver() where A : Activity, A : LifecycleOwner {
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

    override fun print(config: BaseLogKConfig, priority: Int, tag: String, msg: String) {
        _viewProvider.print(config, priority, tag, msg)
    }

    override fun onPause(owner: LifecycleOwner) {
        _isShow = false
        LogKMgr.instance.removePrinter(this)
        super.onPause(owner)
    }
}