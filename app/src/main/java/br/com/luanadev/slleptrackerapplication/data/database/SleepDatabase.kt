package br.com.luanadev.slleptrackerapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.luanadev.slleptrackerapplication.data.entity.SleepNightEntity
import br.com.luanadev.slleptrackerapplication.data.dao.SleepDao

@Database(entities = [SleepNightEntity::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

    companion object {
        var sleepDatabase: SleepDatabase? = null

        @Synchronized
        fun getInstace(context: Context): SleepDatabase {
            if (sleepDatabase == null) {
                sleepDatabase = Room.databaseBuilder(
                    context,
                    SleepDatabase::class.java,
                    "sleep_history_database"
                ).build()
            }
            return sleepDatabase!!
        }
    }

    abstract val sleepDao: SleepDao
}