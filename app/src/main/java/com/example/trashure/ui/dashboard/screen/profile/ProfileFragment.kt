package com.example.trashure.ui.dashboard.screen.profile

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.trashure.R
import com.example.trashure.data.response.DetailUser
import com.example.trashure.databinding.FragmentProfileBinding
import com.example.trashure.ui.about.AboutActivity
import com.example.trashure.ui.login.LoginActivity
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
            val updatedUser = DetailUser(
                username = binding.etUsername.text.toString(),
                nomor = binding.etPhoneNumber.text.toString(),
                provinsi = binding.etProvince.text.toString(),
                kabKota = binding.etCity.text.toString(),
                kecamatan = binding.etSubdistrict.text.toString()
            )
            viewModel.updateUser(updatedUser)
            Toast.makeText(requireContext(), "Profile berhasil dirubah", Toast.LENGTH_SHORT).show()
        }

        binding.btnChangePicture.setOnClickListener {
            showPictureDialog()
        }

        binding.aboutLayout.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        binding.languageLayout.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.btnLogout.setOnClickListener {
            openLogoutDial()
        }
    }

    private fun openLogoutDial() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.message_logout_confirm))
            ?.setPositiveButton(getString(R.string.btn_logout)) { _, _ ->
                viewModel.logout()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().supportFragmentManager.popBackStack()
            }
            ?.setNegativeButton(getString(R.string.action_cancel), null)
        val alert = alertDialog.create()
        alert.show()
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

        viewModel.updateProfileResult.observe(viewLifecycleOwner) { result ->
            result.fold(
                onSuccess = {
                    setEditMode(false)
                },
                onFailure = {
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}