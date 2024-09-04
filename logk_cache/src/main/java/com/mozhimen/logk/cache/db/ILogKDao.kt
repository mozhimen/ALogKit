package com.mozhimen.logk.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mozhimen.logk.cache.mos.LogKRecordCache


/**
 * @ClassName ILogK2Dao
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 14:51
 * @Version 1.0
 */
@Dao
interface ILogKDao {
    @Query("select * from m_logk2")
    fun selectMLogs(): List<LogKRecordCache>

    @Query("delete from m_logk2 where id = :id")
    fun deleteById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMLog(log: LogKRecordCache)
}