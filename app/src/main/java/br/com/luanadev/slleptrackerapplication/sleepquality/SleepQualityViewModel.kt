package br.com.luanadev.slleptrackerapplication.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luanadev.slleptrackerapplication.database.SleepDatabaseDao
import kotlinx.coroutines.*


class SleepQualityViewModel(
    private val sleepNightKey: Long = 0L,
    val database: SleepDatabaseDao) : ViewModel() {

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigating() {
        _navigateToSleepTracker.value = null
    }


    fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            //withContext(Dispatchers.IO) {
            val tonight = database.get(sleepNightKey) ?: return@launch
            tonight.sleepQuality = quality
            database.update(tonight)
            //}

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleepTracker.value = true
        }
    }
}