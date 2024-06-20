package com.example.trashure.ui.dashboard.screen.profile

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.trashure.databinding.FragmentProfileBinding
import com.example.trashure.utils.getImageUri
import com.example.trashure.viewModelFactory.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var currentImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initUI()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditMode(false)

        binding.btnEditProfile.setOnClickListener {
            setEditMode(true)
        }

        binding.btnSaveProfile.setOnClickListener {
            setEditMode(false)
        }

        binding.btnChangePicture.setOnClickListener {
            showPictureDialog()
        }
    }

    private fun showPictureDialog() {
        val options = arrayOf("Open Gallery", "Open Camera")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Option")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "Empty Photos")
        }
    }

    private val launcherIntentCamera =
        registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                showImage()
            }
        }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imgProfile.setImageURI(it)
        }
    }

    private fun setEditMode(isEditMode: Boolean) {
        binding.apply {
            listOf(
                etUsername,
                etPhoneNumber,
                etProvince,
                etCity,
                etSubdistrict,
                btnChangePicture
            ).forEach {
                it.isEnabled = isEditMode
                it.isClickable = isEditMode
            }

            btnEditProfile.visibility = if (isEditMode) View.GONE else View.VISIBLE
            btnSaveProfile.visibility = if (isEditMode) View.VISIBLE else View.GONE
        }
    }

    private fun initUI() {
        viewModel.onLoad.observe(viewLifecycleOwner) { onLoad ->
            binding.progressBar.apply {
                visibility = if (onLoad) View.VISIBLE else View.GONE
            }
        }

        viewModel.getSession().observe(viewLifecycleOwner) {
            viewModel.getUserById()
        }

        viewModel.image.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it)
                .into(binding.imgProfile)
        }
        viewModel.userName.observe(viewLifecycleOwner) {
            binding.tvUsernameProfile.text = it
            binding.etUsername.setText(it)
        }
        viewModel.email.observe(viewLifecycleOwner) {
            binding.tvEmailProfile.text = it
        }
        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.etPhoneNumber.setText(it)
        }
        viewModel.province.observe(viewLifecycleOwner) {
            binding.etProvince.setText(it)
        }
        viewModel.city.observe(viewLifecycleOwner) {
            binding.etCity.setText(it)
        }
        viewModel.subdistrict.observe(viewLifecycleOwner) {
            binding.etSubdistrict.setText(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}