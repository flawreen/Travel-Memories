package com.example.travelmemories.ui.home

import android.annotation.SuppressLint
import android.content.res.Resources
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.travelmemories.MemoryDao
import com.example.travelmemories.MemoryDb
import com.example.travelmemories.MemoryEntity
import com.example.travelmemories.R
import com.example.travelmemories.databinding.FragmentAddItemBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
enum class MOOD { SAD, NORMAL, HAPPY }
class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.title = "Add Memory"
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false)
        val sliderItems = listOf("Personal", "Work")
        val sliderAdapter = ArrayAdapter(requireContext(), R.layout.slider_items, sliderItems)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(sliderAdapter)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleView = binding.inputName
        val locationView = binding.inputLocation
        val notesView = binding.inputNotes
        var moodView: String = "Normal"
        binding.tvDate.text = "No date found"

        // Validate input function
        fun validateInput(): Boolean {
//            Log.i("validateInput function", "FUNCTION CALLED, ${titleView.text.toString()}, ${locationView.text.toString()}")

            return (titleView.text.toString().isNotEmpty() && locationView.text.toString()
                .isNotEmpty() && notesView.text.toString().isNotEmpty() && binding.menu.editText?.text.toString().isNotEmpty())
        }
        // CLOSE Button
        binding.btnClose.setOnClickListener {
            binding.inputName.text = null
            binding.inputLocation.text = null
            findNavController().navigate(R.id.action_addItemFragment_to_nav_home)
        }
        // SAVE Button
        binding.btnSave.setOnClickListener {
            if (validateInput()) {
                val action = AddItemFragmentDirections.actionAddItemFragmentToNavHome(
                    titleView.text.toString(),
                    locationView.text.toString(),
                    notesView.text.toString(),
                    binding.tvDate.text.toString(),
                    moodView,
                    binding.menu.editText?.text.toString()
                )
                findNavController().navigate(action)
                binding.inputName.text = null
                binding.inputLocation.text = null
            }
        }

        // MOOD SLIDER
        binding.sliderMood.addOnChangeListener { _, value, _ ->
            when (value) {
                0.0F -> {
                    binding.tvMood.text = "Sad"
                    moodView = "Sad"
                }
                50.0F -> {
                    binding.tvMood.text = "Normal"
                    moodView = "Normal"
                }
                else -> {
                    binding.tvMood.text = "Happy"
                    moodView = "Happy"
                }
            }
        }

        // DATE PICKER
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        binding.btnDate.setOnClickListener {
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                binding.tvDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                        "${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"
            }
            datePicker.show(parentFragmentManager, "")
        }


    }

}