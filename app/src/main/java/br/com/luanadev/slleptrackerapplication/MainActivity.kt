package br.com.luanadev.slleptrackerapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.luanadev.slleptrackerapplication.databinding.FragmentSleepTrackerBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding {
        FragmentSleepTrackerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}