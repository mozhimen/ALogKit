package com.mozhimen.logk.monitor

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mozhimen.kotlin.elemk.android.view.cons.CWinMgr
import com.mozhimen.kotlin.elemk.kotlin.impls.properties.VarProperty_SetVaryNonnull
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_SYSTEM_ALERT_WINDOW
import com.mozhimen.kotlin.utilk.bases.BaseUtilK
import com.mozhimen.kotlin.utilk.wrapper.UtilKPermission
import com.mozhimen.kotlin.utilk.android.app.UtilKActivityStart
import com.mozhimen.kotlin.utilk.wrapper.UtilKRes
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import com.mozhimen.kotlin.utilk.android.util.e
import com.mozhimen.kotlin.utilk.wrapper.UtilKScreen
import com.mozhimen.kotlin.utilk.android.view.UtilKWindowManager
import com.mozhimen.kotlin.utilk.android.widget.showToastOnMain
import com.mozhimen.kotlin.utilk.androidx.lifecycle.handleLifecycleEventOnStart
import com.mozhimen.kotlin.utilk.androidx.lifecycle.handleLifecycleEventOnStop
import com.mozhimen.kotlin.utilk.java.lang.UtilKThread
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM.lazy_ofNone
import com.mozhimen.logk.basic.bases.BaseLogKRecord
import com.mozhimen.logk.basic.commons.ILogK
import com.mozhimen.logk.basic.commons.ILogKConfig
import com.mozhimen.logk.basic.commons.ILogKPrinter
import com.mozhimen.logk.monitor.commons.ILogKPrinterMonitor
import com.mozhimen.logk.view.LogKPrinterItem
import com.mozhimen.xmlk.recyclerk.item.RecyclerKItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName PrinterMonitorProvider
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 18:52
 * @Version 1.0
 */
@OPermission_SYSTEM_ALERT_WINDOW
class LogKPrinterMonitorDelegate(private val _logk: ILogK) : ILogKPrinter, ILogKPrinterMonitor, BaseUtilK(), LifecycleOwner {
    private val TITLE_OPEN_PANEL by lazy_ofNone { UtilKRes.getString_ofContext(com.mozhimen.logk.view.R.string.logk_view_provider_title_open) }
    private val TITLE_CLOSE_PANEL by lazy_ofNone { UtilKRes.getString_ofContext(com.mozhimen.logk.view.R.string.logk_view_provider_title_close) }

    private val _layoutParams: WindowManager.LayoutParams by lazy_ofNone { WindowManager.LayoutParams() }
    private var _rootView: FrameLayout? = null
        @SuppressLint("InflateParams")
        get() {
            if (field != null) return field
            val frameLayout = LayoutInflater.from(_context).inflate(R.layout.logk_monitor_view, null, false) as FrameLayout
            frameLayout.tag = com.mozhimen.logk.basic.cons.CLogKCons.TAG_LOGK_MONITOR_VIEW
            return frameLayout.also { field = it }
        }
    private val _windowManager: WindowManager by lazy_ofNone { UtilKWindowManager.get(_context) }
    private val _adapterKItemRecycler by lazy_ofNone { RecyclerKItemAdapter() }

    private var _recyclerView: RecyclerView? = null
        get() {
            if (field != null) return field
            val recyclerView = _rootView!!.findViewById<RecyclerView>(R.id.logk_monitor_view_msg)
            recyclerView.layoutManager = LinearLayoutManager(_context)
            recyclerView.adapter = _adapterKItemRecycler
            return recyclerView.also { field = it }
        }

    private var _titleView: TextView? = null
        get() {
            if (field != null) return field
            val textView = _rootView!!.findViewById<TextView>(R.id.logk_monitor_view_title)
            textView.setOnClickListener { if (_isFold) unfold() else fold() }
            return textView.also { field = it }
        }

    private var _isFold = false
        set(value) {
            _titleView!!.text = if (value) TITLE_OPEN_PANEL else TITLE_CLOSE_PANEL
            field = value
        }

    private var _isOpen by VarProperty_SetVaryNonnull(false) { _, value ->
        if (value) lifecycleRegistry.handleLifecycleEventOnStart() else lifecycleRegistry.handleLifecycleEventOnStop()
        true
    }

    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }
    //////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        _layoutParams.flags = (CWinMgr.Lpf.NOT_TOUCH_MODAL or CWinMgr.Lpf.NOT_FOCUSABLE) or CWinMgr.Lpf.FULLSCREEN
        _layoutParams.format = PixelFormat.TRANSLUCENT
        _layoutParams.gravity = Gravity.END or Gravity.BOTTOM
        _layoutParams.type = if (UtilKBuildVersion.isAfterV_26_8_O()) CWinMgr.Lpt.APPLICATION_OVERLAY else CWinMgr.Lpt.TOAST
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override fun print(config: ILogKConfig, priority: Int, tag: String, msg: String) {
        if (_isOpen) {
            if (UtilKThread.isMainThread()) {
                printInView(priority, tag, msg)
            } else {
                lifecycleScope.launch(Dispatchers.Main) {
                    printInView(priority, tag, msg)
                }
            }
        }
    }

    override fun isOpen(): Boolean =
        _isOpen

    override fun toggle() {
        toggle(true)
    }

    override fun toggle(isFold: Boolean) {
        if (isOpen())
            close()
        else
            open(isFold)
    }

    override fun open() {
        open(true)
    }

    override fun open(isFold: Boolean) {
        if (_isOpen) return
        if (!UtilKPermission.hasSystemAlertWindow()) {
            _logk.etk(TAG, "PrinterMonitor play app has no overlay permission")
            "请打开悬浮窗权限".showToastOnMain()
            UtilKActivityStart.startSettingManageOverlayPermission(_context)
            return
        }
        try {
            _windowManager.addView(_rootView, getWindowLayoutParams(isFold))
            if (isFold)
                fold()
            else unfold()
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        _isOpen = true
    }

    override fun close() {
        if (!_isOpen) return
        try {
            if (_rootView!!.findViewWithTag<View?>(com.mozhimen.logk.basic.cons.CLogKCons.TAG_LOGK_MONITOR_VIEW) == null) return
            _windowManager.removeView(_rootView)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        _isOpen = false
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun printInView(level: Int, tag: String, msg: String) {
        _adapterKItemRecycler.addItem(LogKPrinterItem(BaseLogKRecord(System.currentTimeMillis(), level, tag, msg)), true)
        _recyclerView!!.smoothScrollToPosition(_adapterKItemRecycler.itemCount - 1)
    }

    @Throws(Exception::class)
    private fun fold() {
        if (_isFold) return
        _isFold = true
        _titleView!!.text = TITLE_OPEN_PANEL
        _recyclerView!!.visibility = View.GONE
        _rootView!!.layoutParams = getLayoutParams(true)
        _windowManager.updateViewLayout(_rootView, getWindowLayoutParams(true))
    }

    @Throws(Exception::class)
    private fun unfold() {
        if (!_isFold) return
        _isFold = false
        _titleView!!.text = TITLE_CLOSE_PANEL
        _recyclerView!!.visibility = View.VISIBLE
        _rootView!!.layoutParams = getLayoutParams(false)
        _windowManager.updateViewLayout(_rootView, getWindowLayoutParams(false))
    }

    private fun getLayoutParams(isFold: Boolean): FrameLayout.LayoutParams {
        val layoutParams = (_rootView!!.layoutParams as? FrameLayout.LayoutParams?) ?: FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        if (isFold) {
            if (_titleView!!.width == 0 || _titleView!!.height == 0) {
                _isFold = true
                return layoutParams
            }
            layoutParams.width = _titleView!!.width
            layoutParams.height = _titleView!!.height
        } else {
            layoutParams.width = UtilKScreen.getWidth()
            layoutParams.height = UtilKScreen.getHeight() / 3
        }
        return layoutParams
    }

    private fun getWindowLayoutParams(isFold: Boolean): WindowManager.LayoutParams {
        _layoutParams.width = if (isFold) CWinMgr.Lp.WRAP_CONTENT else CWinMgr.Lp.MATCH_PARENT
        _layoutParams.height = if (isFold) CWinMgr.Lp.WRAP_CONTENT else (UtilKScreen.getHeight() / 3)
        return _layoutParams
    }


}