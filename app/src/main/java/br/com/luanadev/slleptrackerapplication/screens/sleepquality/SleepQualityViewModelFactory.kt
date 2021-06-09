package br.com.luanadev.slleptrackerapplication.screens.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.luanadev.slleptrackerapplication.data.dao.SleepDao

class SleepQualityViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: SleepDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}