package br.com.luanadev.slleptrackerapplication

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import br.com.luanadev.slleptrackerapplication.database.SleepDatabase
import br.com.luanadev.slleptrackerapplication.database.SleepDao
import br.com.luanadev.slleptrackerapplication.database.SleepNightEntity
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SleepDatabaseTest {

    private lateinit var sleepDao: SleepDao
    private lateinit var db: SleepDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.sleepDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNightEntity()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }
}