package com.example.travelmemories.ui.home

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.travelmemories.MemoryEntity
import com.example.travelmemories.R
import com.example.travelmemories.databinding.ItemBinding
import com.example.travelmemories.databinding.MemoryDetailsBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
@BindingAdapter("nume")
fun TextView.setNume(name: String) {
    text = name
}

@BindingAdapter("locatie")
fun TextView.setLocatie(name: String) {
    text = name
}

@BindingAdapter("data_plecarii")
fun TextView.setDataPlecarii(name: String) {
    text = name
}

@BindingAdapter("val_name")
fun TextView.setValName(s: String) {
    text = s
}

@BindingAdapter("val_location")
fun TextView.setValLocation(s: String) {
    text = s
}

@BindingAdapter("val_date")
fun TextView.setValDate(s: String) {
    text = s
}

@BindingAdapter("val_type")
fun TextView.setValType(s: String) {
    text = s
}

@BindingAdapter("val_mood")
fun TextView.setValMood(s: String) {
    text = s
}

@BindingAdapter("val_notes")
fun TextView.setValNotes(s: String) {
    text = s
}

class Adapter(
    var data: List<MemoryEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var switchLayoutt: Int = 0

    override fun getItemViewType(position: Int): Int {
        return when (switchLayoutt) {
            0 -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (switchLayoutt) {
            0 -> ViewHolder.from(parent)
            else -> DetailsViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.itemView.setOnClickListener {
                    switchLayoutt = 1
                    notifyDataSetChanged()
                    holder.binding.executePendingBindings()
                }
                holder.binding.memoryName = data[position].name
                holder.binding.locationName = data[position].location
                holder.binding.date = data[position].date
                holder.binding.executePendingBindings()
            }
            is DetailsViewHolder -> {
                holder.binding.btnBack.setOnClickListener {
                    switchLayoutt = 0
                    notifyDataSetChanged()
                    holder.binding.executePendingBindings()
                }
                holder.binding.memoryName = data[position].name
                holder.binding.locationName = data[position].location
                holder.binding.memoryDate = data[position].date
                holder.binding.memoryType = data[position].type
                holder.binding.memoryMood = data[position].mood
                holder.binding.memoryNotes = data[position].notes
                holder.binding.executePendingBindings()
            }
        }

    }

    override fun getItemCount(): Int = data.size

    class ViewHolder private constructor(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DetailsViewHolder private constructor(val binding: MemoryDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DetailsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemoryDetailsBinding.inflate(layoutInflater, parent, false)
                return DetailsViewHolder(binding)
            }
        }
    }

}