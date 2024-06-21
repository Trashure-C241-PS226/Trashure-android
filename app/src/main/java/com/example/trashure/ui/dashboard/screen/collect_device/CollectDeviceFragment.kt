package com.example.trashure.ui.dashboard.screen.collect_device

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trashure.data.model.Predict
import com.example.trashure.data.response.ApiResponse
import com.example.trashure.databinding.FragmentCollectDeviceBinding
import com.example.trashure.ui.setor.prediction.PredictionActivity
import com.example.trashure.utils.getImageUri
import com.example.trashure.viewModelFactory.ViewModelFactory

class CollectDeviceFragment : Fragment() {

    private var _binding: FragmentCollectDeviceBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CollectDeviceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var currentImageUri: Uri? = null

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
        binding.uploadImg.setOnClickListener {
            startCamera()
        }
    }

    private fun predictItem() {
        val brand = binding.etCollectBrand.text.toString()
        val storage = binding.etCollectStorage.text.toString()
        val ram = binding.etCollectRam.text.toString()
        val screenSize = binding.etCollectScreen.text.toString()
        val camera = binding.etCollectCamera.text.toString()
        val batteryCapacity = binding.etCollectBattery.text.toString()
        val tahunPemakaian = binding.etCollectDuration.text.toString().toIntOrNull() ?: 0

        viewModel.predictItem(
            brand,
            storage,
            ram,
            screenSize,
            camera,
            batteryCapacity,
            tahunPemakaian
        ).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                when (response) {
                    is ApiResponse.Loading -> {
                        showLoading(true)
                    }

                    is ApiResponse.Success -> {
                        showLoading(false)
                        val category = response.data.predictDetail?.category
                        val harga = response.data.predictDetail?.harga
                        viewModel.savePredict(Predict(category!!, harga!!))
                        currentImageUri?.let { uri ->
                            val intent = Intent(requireContext(), PredictionActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra("IMAGE_URI", uri)
                            startActivity(intent)
                            showToast(response.data.message)
                        }
                    }

                    is ApiResponse.Error -> {
                        showLoading(false)
                        showToast(response.error)
                    }
                }
            }
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
            binding.tvUpload.isInvisible = true
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.uploadImg.setImageURI(it)
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