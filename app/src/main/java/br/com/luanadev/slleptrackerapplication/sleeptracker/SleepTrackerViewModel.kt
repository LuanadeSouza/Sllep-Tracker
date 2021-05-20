package br.com.luanadev.slleptrackerapplication.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.luanadev.slleptrackerapplication.database.SleepDao

class SleepTrackerViewModel(
    val database: SleepDao,
    application: Application) : AndroidViewModel(application) {
}