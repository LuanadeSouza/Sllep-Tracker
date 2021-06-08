package br.com.luanadev.slleptrackerapplication.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.luanadev.slleptrackerapplication.data.database.SleepDatabase
import br.com.luanadev.slleptrackerapplication.databinding.FragmentSleepQualityBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class SleepQualityFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSleepQualityBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBase()
    }

    private fun initDataBase() {
        val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())
        val dataSource = context?.let { SleepDatabase.getInstance(it).sleepDao }
        val viewModelFactory =
            dataSource?.let { SleepQualityViewModelFactory(arguments.sleepNightKey, it) }
        val sleepQualityViewModel = viewModelFactory?.let {
            ViewModelProvider(
                this, it
            ).get(SleepQualityViewModel::class.java)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sleepQualityViewModel = sleepQualityViewModel
        sleepQualityViewModel?.navigateToSleepTracker?.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment()
                )
                sleepQualityViewModel.doneNavigating()
            }
        })
    }
}
