package br.com.luanadev.slleptrackerapplication.screens.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.luanadev.slleptrackerapplication.R
import br.com.luanadev.slleptrackerapplication.data.database.SleepDatabase
import br.com.luanadev.slleptrackerapplication.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

class SleepTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = SleepNightAdapter()
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_tracker, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(SleepTrackerViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sleepTrackerViewModel = sleepTrackerViewModel
        binding.sleepList.adapter = adapter
        sleepTrackerViewModel.nights.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                sleepTrackerViewModel.doneShowingSnackbar()
            }
        })

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
                )
                sleepTrackerViewModel.doneNavigating()
            }
        })
        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        // val manager = GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)

        binding.sleepList.layoutManager = manager

        return binding.root
    }
}
