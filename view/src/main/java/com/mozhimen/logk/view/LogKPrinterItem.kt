package com.mozhimen.logk.view

import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.mozhimen.kotlin.elemk.android.util.annors.ALog
import com.mozhimen.kotlin.elemk.android.util.cons.CLog
import com.mozhimen.kotlin.utilk.android.content.UtilKPackage
import com.mozhimen.kotlin.utilk.wrapper.UtilKRes
import com.mozhimen.kotlin.utilk.kotlin.text.replaceRegexLineBreak
import com.mozhimen.logk.basic.commons.ILogKRecord
import com.mozhimen.logk.view.databinding.LogkPrinterViewItemBinding
import com.mozhimen.xmlk.recyclerk.item.RecyclerKItem
import com.mozhimen.xmlk.vhk.VHKRecyclerVDB

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */
class LogKPrinterItem(private val _record: ILogKRecord) : RecyclerKItem<VHKRecyclerVDB<LogkPrinterViewItemBinding>>() {
    override fun onBindItem(holder: VHKRecyclerVDB<LogkPrinterViewItemBinding>, position: Int) {
        super.onBindItem(holder, position)
        val intColor = getIntColorFor(_record.priority)
        holder.vdb.logkPrinterViewTag.text = _record.getFlattened()
        holder.vdb.logkPrinterViewTag.setTextColor(intColor)
        holder.vdb.logkPrinterViewMsg.text = _record.msg.replaceRegexLineBreak().replace(UtilKPackage.getPackageName(), "")
        holder.vdb.logkPrinterViewMsg.setTextColor(intColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVDB<LogkPrinterViewItemBinding> =
        VHKRecyclerVDB(getItemLayoutId(), parent)

    override fun getItemLayoutId(): Int =
        com.mozhimen.logk.view.R.layout.logk_printer_view_item

    @ColorInt
    fun getIntColorFor(@ALog priority: Int): Int =
        UtilKRes.gainColor(
            when (priority) {
                CLog.VERBOSE -> com.mozhimen.logk.view.R.color.logk_v
                CLog.DEBUG -> com.mozhimen.logk.view.R.color.logk_d
                CLog.INFO -> com.mozhimen.logk.view.R.color.logk_i
                CLog.WARN -> com.mozhimen.logk.view.R.color.logk_w
                CLog.ERROR -> com.mozhimen.logk.view.R.color.logk_e
                else -> -0x100
            }
        )
}