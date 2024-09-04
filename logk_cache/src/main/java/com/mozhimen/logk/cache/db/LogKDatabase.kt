package com.mozhimen.logk.cache.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mozhimen.kotlin.utilk.android.app.UtilKApplicationWrapper
import com.mozhimen.logk.cache.mos.LogKRecordCache


/**
 * @ClassName LogK2Database
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 14:44
 * @Version 1.0
 */
@Database(entities = [LogKRecordCache::class], version = 1, exportSchema = false)
abstract class LogKDatabase : RoomDatabase() {
    abstract val logk2Dao: ILogKDao

    companion object {
        @Volatile
        private var _db: LogKDatabase =
            Room.databaseBuilder(UtilKApplicationWrapper.instance.applicationContext, LogKDatabase::class.java, "logk2_db").allowMainThreadQueries().build()

        val db: LogKDatabase
            get() = _db

        val dao: ILogKDao =
            _db.logk2Dao
    }
}