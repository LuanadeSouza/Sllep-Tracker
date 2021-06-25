package br.com.luanadev.slleptrackerapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.luanadev.slleptrackerapplication.data.entity.SleepNightEntity

@Dao
interface SleepDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(night: SleepNightEntity)

    @Update
    suspend fun update(night: SleepNightEntity)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend fun get(key: Long): SleepNightEntity?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNightEntity>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight(): SleepNightEntity?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepNightEntity>

    @Delete
    suspend fun clearAllNigths(night: SleepNightEntity)
}