package com.example.trashure.ui.dashboard.screen.collect_device

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.databinding.FragmentCollectDeviceBinding
import com.example.trashure.ui.setor.prediction.PredictionActivity
import com.example.trashure.viewModelFactory.ViewModelFactory

class CollectDeviceFragment : Fragment() {

    private var _binding: FragmentCollectDeviceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CollectDeviceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

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
            predictItem()
        }
    }

    private fun predictItem() {
        val brand = binding.etCollectBrand.text.toString()
        val model = binding.etCollectModel.text.toString()
        val storage = binding.etCollectStorage.text.toString()
        val ram = binding.etCollectRam.text.toString()
        val screenSize = binding.etCollectScreen.text.toString()
        val camera = binding.etCollectCamera.text.toString()
        val batteryCapacity = binding.etCollectBattery.text.toString()
        val tahunPemakaian = binding.etCollectDuration.text.toString().toIntOrNull() ?: 0

        viewModel.predictItem(
            brand,
            model,
            storage,
            ram,
            screenSize,
            camera,
            batteryCapacity,
            tahunPemakaian
        ).observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is ApiResponse.Loading -> {
                        showLoading(true)
                    }

                    is ApiResponse.Success -> {
                        showLoading(false)
                        val intent = Intent(requireContext(), PredictionActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                    is ApiResponse.Error -> {
                        showLoading(false)
                        showToast(it.error)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            listOf(
                etCollectBrand, etCollectModel, etCollectStorage, etCollectRam,
                etCollectDuration, etCollectScreen, etCollectCamera, etCollectBattery
            ).forEach { editText ->
                editText.isEnabled = !isLoading
                editText.isClickable = !isLoading
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}