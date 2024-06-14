package com.example.trashure.ui.dashboard.screen.collect_device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.trashure.databinding.FragmentCollectDeviceBinding

class CollectDeviceFragment : Fragment() {

    private var _binding: FragmentCollectDeviceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val collectDeviceViewModel =
            ViewModelProvider(this).get(CollectDeviceViewModel::class.java)

        _binding = FragmentCollectDeviceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCollectDevice
        collectDeviceViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}