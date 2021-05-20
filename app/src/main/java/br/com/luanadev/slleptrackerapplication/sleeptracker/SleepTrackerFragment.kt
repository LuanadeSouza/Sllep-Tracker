package br.com.luanadev.slleptrackerapplication.sleeptracker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.luanadev.slleptrackerapplication.R
import br.com.luanadev.slleptrackerapplication.databinding.FragmentSleepTrackerBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class SleepTrackerFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSleepTrackerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

}
