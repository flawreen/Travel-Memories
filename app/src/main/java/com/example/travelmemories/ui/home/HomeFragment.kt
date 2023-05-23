package com.example.travelmemories.ui.home

import android.media.CamcorderProfile.getAll
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelmemories.MemoryDao
import com.example.travelmemories.MemoryDb
import com.example.travelmemories.MemoryEntity
import com.example.travelmemories.R
import com.example.travelmemories.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Console

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var args: HomeFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dao: MemoryDao
    private lateinit var data: MutableList<MemoryEntity>
    private lateinit var adapter: Adapter
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        dao = MemoryDb.getInstance(binding.root.context).memoryDao()

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_addItemFragment)
        }

        val rv: RecyclerView = binding.rv
        rv.layoutManager = LinearLayoutManager(context)
        adapter = Adapter(homeViewModel.allQueries.value ?: emptyList())
        rv.adapter = adapter
        adapter.notifyDataSetChanged()


        return binding.root
    }


    override fun onResume() {
        homeViewModel.getAll()
        if (args.name != null) {
            homeViewModel.insert(
                MemoryEntity(
                    args.name!!, args.location!!
                    , args.date ?: "No date found"
                    , args.type ?: "Personal"
                    , args.mood ?: "Normal"
                    , args.notes ?: "No notes found")
            )
            adapter.notifyDataSetChanged()
            args = null
        }

        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}