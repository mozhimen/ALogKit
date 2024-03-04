package com.mozhimen.logk.temps.printer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.mozhimen.basick.elemk.android.util.annors.ALogPriority
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import com.mozhimen.uicorek.recyclerk.item.RecyclerKItem
import com.mozhimen.uicorek.vhk.VHKRecyclerVDB
import com.mozhimen.logk.bases.BaseLogKRecord
import com.mozhimen.logk.databinding.LogkPrinterViewItemBinding

/**
 * @ClassName PrinterViewItem
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/23 11:51
 * @Version 1.0
 */
class LogKPrinterItem<R : BaseLogKRecord>(private val _record: R) : RecyclerKItem<VHKRecyclerVDB<LogkPrinterViewItemBinding>>() {
    override fun onBindItem(holder: VHKRecyclerVDB<LogkPrinterViewItemBinding>, position: Int) {
        super.onBindItem(holder, position)
        val intColor = getIntColorFor(_record.priority)
        holder.vdb.logkPrinterViewTag.text = _record.getFlattened()
        holder.vdb.logkPrinterViewTag.setTextColor(intColor)
        holder.vdb.logkPrinterViewMsg.text = _record.msg.replaceRegexLineBreak().replace(UtilKPackage.getPackageName(), "")
        holder.vdb.logkPrinterViewMsg.setTextColor(intColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup): VHKRecyclerVDB<LogkPrinterViewItemBinding> =
        VHKRecyclerVDB(LayoutInflater.from(parent.context).inflate(getItemLayoutId(), parent, false))

    override fun getItemLayoutId(): Int =
        com.mozhimen.logk.R.layout.logk_printer_view_item

    @ColorInt
    fun getIntColorFor(@ALogPriority priority: Int): Int =
        UtilKRes.gainColor(
            when (priority) {
                CLogPriority.V -> com.mozhimen.logk.R.color.logk_v
                CLogPriority.D -> com.mozhimen.logk.R.color.logk_d
                CLogPriority.I -> com.mozhimen.logk.R.color.logk_i
                CLogPriority.W -> com.mozhimen.logk.R.color.logk_w
                CLogPriority.E -> com.mozhimen.logk.R.color.logk_e
                else -> -0x100
            }
        )
}