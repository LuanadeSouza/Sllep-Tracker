package br.com.luanadev.slleptrackerapplication.screens.sleepquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.luanadev.slleptrackerapplication.R
import br.com.luanadev.slleptrackerapplication.data.database.SleepDatabase
import br.com.luanadev.slleptrackerapplication.databinding.FragmentSleepQualityBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar

class SleepQualityFragment : Fragment() {

    private val binding by viewBinding {
        FragmentSleepQualityBinding.inflate(layoutInflater)
    }
    val arguments: SleepQualityFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val dataSource = context?.let { SleepDatabase.getInstace(it).sleepDao }
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
        sleepQualityViewModel?.showSnackBarEvent?.observe(viewLifecycleOwner, {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.add_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                sleepQualityViewModel.doneShowingSnackbar()
            }
        })
    }
}