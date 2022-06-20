package com.example.exampleapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.exampleapplication.databinding.ListFragmentBinding
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater)

        with(binding) {
            //add event handlers here, button clicks, etc
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listStateFlow.collect {
                    //handle list screen state changes here
                }
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance() = ListFragment()
    }
}