package br.com.luanadev.slleptrackerapplication.screens.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import br.com.luanadev.slleptrackerapplication.data.dao.SleepDao
import br.com.luanadev.slleptrackerapplication.data.entity.SleepNightEntity
import br.com.luanadev.slleptrackerapplication.formatNights
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    dataSource: SleepDao,
    application: Application) : ViewModel() {

    val database = dataSource
    private var tonight = MutableLiveData<SleepNightEntity?>()

    val nights = database.getAllNights()
    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }

    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }

    val clearButtonVisible = Transformations.map(nights) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean?>()

    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackbarEvent

    private val _navigateToSleepQuality = MutableLiveData<SleepNightEntity>()

    val navigateToSleepQuality: LiveData<SleepNightEntity>
        get() = _navigateToSleepQuality

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = null
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _navigateToSleepDetail = MutableLiveData<Long>()
    val navigateToSleepDetail
        get() = _navigateToSleepDetail

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDetail.value = id
    }

    fun onSleepDetailNavigated() {
        _navigateToSleepDetail.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNightEntity? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    private suspend fun insert(night: SleepNightEntity) {
        database.insert(night)
    }

    private suspend fun update(night: SleepNightEntity) {
        database.update(night)
    }

    private suspend fun clear() {
        database.clear()
    }

    fun onStart() {
        viewModelScope.launch {
            val newNight = SleepNightEntity()

            insert(newNight)

            tonight.value = getTonightFromDatabase()
        }
    }

    fun onStop() {
        viewModelScope.launch {

            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
            _showSnackbarEvent.value = true
        }
    }
}