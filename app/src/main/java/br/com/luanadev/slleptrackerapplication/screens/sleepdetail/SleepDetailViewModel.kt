package br.com.luanadev.slleptrackerapplication.screens.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.luanadev.slleptrackerapplication.data.dao.SleepDao
import br.com.luanadev.slleptrackerapplication.data.entity.SleepNightEntity

class SleepDetailViewModel(
    private val sleepNightKey: Long = 0L,
    dataSource: SleepDao
) : ViewModel() {

    val database = dataSource

    private val night: LiveData<SleepNightEntity>

    fun getNight() = night

    init { night = database.getNightWithId(sleepNightKey) }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() { _navigateToSleepTracker.value = null }

    fun onClose() { _navigateToSleepTracker.value = true }

}
