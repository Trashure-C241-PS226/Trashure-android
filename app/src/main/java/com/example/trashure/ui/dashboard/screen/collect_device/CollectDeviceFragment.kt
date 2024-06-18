package com.example.trashure.ui.dashboard.screen.collect_device

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trashure.databinding.FragmentCollectDeviceBinding
import com.example.trashure.ui.setor.prediction.PredictionActivity

class CollectDeviceFragment : Fragment() {

    private var _binding: FragmentCollectDeviceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectDeviceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPredict.setOnClickListener {
            val intent = Intent(requireContext(), PredictionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}