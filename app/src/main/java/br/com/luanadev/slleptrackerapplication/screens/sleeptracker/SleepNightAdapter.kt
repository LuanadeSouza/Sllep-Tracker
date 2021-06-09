package br.com.luanadev.slleptrackerapplication.screens.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.luanadev.slleptrackerapplication.R
import br.com.luanadev.slleptrackerapplication.convertDurationToFormatted
import br.com.luanadev.slleptrackerapplication.convertNumericQualityToString
import br.com.luanadev.slleptrackerapplication.data.entity.SleepNightEntity
import br.com.luanadev.slleptrackerapplication.databinding.ListItemSleepNightBinding

class SleepNightAdapter : ListAdapter<SleepNightEntity, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemSleepNightBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNightEntity) {
            binding.sleep = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNightEntity>() {

    override fun areItemsTheSame(oldItem: SleepNightEntity, newItem: SleepNightEntity): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNightEntity, newItem: SleepNightEntity): Boolean {
        return oldItem == newItem
    }
}